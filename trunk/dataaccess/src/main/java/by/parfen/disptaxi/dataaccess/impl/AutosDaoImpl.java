package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.AutosDao;
import by.parfen.disptaxi.datamodel.Autos;

@Repository
public class AutosDaoImpl extends AbstractDaoImpl<Long, Autos> implements AutosDao {

	protected AutosDaoImpl() {
		super(Autos.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Autos> root = criteria.from(Autos.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<Autos> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Autos> criteria = cBuilder.createQuery(Autos.class);
		Root<Autos> root = criteria.from(Autos.class);

		criteria.select(root);

		TypedQuery<Autos> query = getEm().createQuery(criteria);
		List<Autos> results = query.getResultList();
		return results;
	}

	@Override
	public List<Autos> getAll(SingularAttribute<Autos, ?> attr, boolean ascending, int startRecord, int pageSize) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Autos> criteria = cBuilder.createQuery(Autos.class);
		Root<Autos> root = criteria.from(Autos.class);

		criteria.select(root);
		criteria.orderBy(new OrderImpl(root.get(attr), ascending));

		TypedQuery<Autos> query = getEm().createQuery(criteria);
		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		List<Autos> results = query.getResultList();
		return results;
	}

}
