package io.beanthemoonman.moonservice.persistence;

import io.beanthemoonman.moonservice.api.model.DataModificationResponse;
import io.beanthemoonman.moonservice.persistence.entities.People;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
public class EntityHelper {

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
}