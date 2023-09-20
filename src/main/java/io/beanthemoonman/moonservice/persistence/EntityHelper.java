package io.beanthemoonman.moonservice.persistence;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.*;
import org.hibernate.Session;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
      logger.log(Level.SEVERE, e.getMessage());
      throw e;
    }
  }

  public <T> List<T> queryEntity(Class<T> entity, String query, String column, Integer start, Integer count) {
    var gateKeptCount = count > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : count;
    var session = entityManager.unwrap(Session.class);
    var cb = session.getCriteriaBuilder();
    var cr = cb.createQuery(entity);
    var root = cr.from(entity);
    cr.select(root).where(cb.like(root.get(column), STR."%\{query}%"));
    var hibernateQuery = session.createQuery(cr)
        .setFetchSize(gateKeptCount)
        .setFirstResult(start);
    return hibernateQuery.getResultList();
  }
}