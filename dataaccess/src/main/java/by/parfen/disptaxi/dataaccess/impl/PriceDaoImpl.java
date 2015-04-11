package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.PriceDao;
import by.parfen.disptaxi.datamodel.Price;

@Repository
public class PriceDaoImpl extends AbstractDaoImpl<Long, Price> implements PriceDao {

	protected PriceDaoImpl() {
		super(Price.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Price> root = criteria.from(Price.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<Price> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Price> criteria = cBuilder.createQuery(Price.class);
		Root<Price> root = criteria.from(Price.class);

		criteria.select(root);

		TypedQuery<Price> query = getEm().createQuery(criteria);
		List<Price> results = query.getResultList();
		return results;
	}

	@Override
	public List<Price> getAll(SingularAttribute<Price, ?> attr, boolean ascending, int startRecord, int pageSize) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Price> criteria = cBuilder.createQuery(Price.class);
		Root<Price> root = criteria.from(Price.class);

		criteria.select(root);
		criteria.orderBy(new OrderImpl(root.get(attr), ascending));

		TypedQuery<Price> query = getEm().createQuery(criteria);
		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		List<Price> results = query.getResultList();
		return results;
	}

}
