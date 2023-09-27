package io.beanthemoonman.moonservice.persistence;

import io.beanthemoonman.moonservice.persistence.model.FilterWrapper;
import io.beanthemoonman.moonservice.persistence.model.OrderWrapper;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.transaction.*;
import org.hibernate.Session;
import org.hibernate.query.criteria.JpaPredicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.beanthemoonman.moonservice.persistence.model.OrderSort.DESC;

@RequestScoped
public class EntityHelper {

  static Integer MAX_PAGE_SIZE = 100;

  @Inject
  Logger logger;

  @PersistenceContext
  EntityManager entityManager;

  @Resource
  UserTransaction userTransaction;

  public void commitEntity(Object entity)
      throws HeuristicRollbackException, SystemException, HeuristicMixedException, NotSupportedException,
      RollbackException {
    try {
      userTransaction.begin();
      entityManager.persist(entity);
      entityManager.flush();
      userTransaction.commit();
    } catch (Exception e) {
      try {
        userTransaction.rollback();
      } catch (SystemException se) {
        logger.log(Level.SEVERE, se.getMessage());
      }
      logger.log(Level.SEVERE, Arrays.asList(e.getMessage(), Objects.toString(entity)).toString());
      throw e;
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public <T> List<T> queryEntity(Class<T> entity, List<FilterWrapper> filterWrappers, List<OrderWrapper> orderWrappers,
      Integer start, Integer count) {
    var gateKeptCount = count > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : count;
    var session = entityManager.unwrap(Session.class);
    var cb = session.getCriteriaBuilder();
    var cr = cb.createQuery(entity);
    var root = cr.from(entity);
    var predicates = new ArrayList<JpaPredicate>();
    for (var query : filterWrappers) {
      predicates.add(switch (query.filterType()) {
        case GREATERTHAN -> cb.greaterThan(root.get(query.column()), (Comparable) query.query());
        case LESSTHAN -> cb.lessThan(root.get(query.column()), (Comparable) query.query());
        case NOTLIKE -> switch (query.query()) {
          case String s -> cb.notLike(root.get(query.column()), STR. "%\{ s }%" );
          default -> cb.notEqual(root.get(query.column()), query.query());
        };
        case LIKE -> switch (query.query()) {
          case String s -> cb.like(root.get(query.column()), STR. "%\{ s }%" );
          default -> cb.equal(root.get(query.column()), query.query());
        };
      });
    }
    var orders = new ArrayList<Order>();
    for (var order : orderWrappers) {
      orders.add(
        order.orderSort() == DESC ? cb.desc(root.get(order.column())) : cb.asc(root.get(order.column()))
      );
    }
    cr.select(root)
        .where(predicates.toArray(new JpaPredicate[0]))
        .orderBy(orders);
    return session.createQuery(cr)
        .setMaxResults(gateKeptCount)
        .setFirstResult(start)
        .getResultList();
  }
}