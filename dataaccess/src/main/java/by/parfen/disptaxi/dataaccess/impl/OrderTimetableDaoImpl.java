package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.OrderTimetableDao;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.OrderTimetable;
import by.parfen.disptaxi.datamodel.OrderTimetable_;

@Repository
public class OrderTimetableDaoImpl extends AbstractDaoImpl<Long, OrderTimetable> implements OrderTimetableDao {

	protected OrderTimetableDaoImpl() {
		super(OrderTimetable.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<OrderTimetable> root = criteria.from(OrderTimetable.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<OrderTimetable> getAllByOrder(Order order) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<OrderTimetable> criteria = cBuilder.createQuery(OrderTimetable.class);
		Root<OrderTimetable> root = criteria.from(OrderTimetable.class);

		criteria.select(root);
		if (order != null) {
			criteria.where(cBuilder.equal(root.get(OrderTimetable_.order), order));
		}

		TypedQuery<OrderTimetable> query = getEm().createQuery(criteria);
		List<OrderTimetable> results = query.getResultList();
		return results;
	}

	@Override
	public List<OrderTimetable> getAll() {
		return this.getAllByOrder(null);
	}

	@Override
	public void deleteByOrderId(Long orderId) {
		String deleteSql = new StringBuilder("DELETE FROM ").append(OrderTimetable.class.getSimpleName())
				.append(" e WHERE ").append(OrderTimetable_.order.getName()).append(" = ").append(orderId).toString();
		getEm().createQuery(deleteSql).executeUpdate();
	}

}
