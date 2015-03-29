package by.parfen.disptaxi.dataaccess.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.Validate;

import by.parfen.disptaxi.dataaccess.AbstractDao;

public abstract class AbstractDaoImpl<ID, Entity> implements
		AbstractDao<ID, Entity> {

	private EntityManager em;

	private final Class<Entity> entityClass;
	private final Class<ID> primaryKeyClass;

	protected AbstractDaoImpl(final Class<ID> primaryKeyClass,
			final Class<Entity> entityClass) {
		Validate.notNull(entityClass, "entityClass could not be a null");
		Validate.notNull(primaryKeyClass, "primaryKeyClass could not be a null");
		this.entityClass = entityClass;
		this.primaryKeyClass = primaryKeyClass;
	}

	@PersistenceContext
	private void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public Entity getById(ID id) {
		return em.find(getEntityClass(), id);
	}

	private Class<Entity> getEntityClass() {
		return entityClass;
	}

	private Class<ID> getPrimaryKeyClass() {
		return primaryKeyClass;
	}

}
