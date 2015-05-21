package by.parfen.disptaxi.webapp.orders.panel;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Order_;
import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.webapp.orders.OrderEditPage;

public class OrderListPanel extends Panel {

	private static final int ITEMS_PER_PAGE = 7;
	private static final String CSS_NONE = "order-result-none";
	private static final String CSS_OK = "order-result-ok";
	private static final String CSS_CANCELLED = "order-result-cancelled";

	@Inject
	OrderService orderService;
	@Inject
	AutoService autoService;

	public OrderListPanel(String id) {
		super(id);
		OrderDataProvider orderDataProvider = new OrderDataProvider();

		final WebMarkupContainer tableBody = new WebMarkupContainer("tableBody");

		tableBody.setOutputMarkupId(true);
		add(tableBody);

		DataView<Order> dataView = new DataView<Order>("tableRows", orderDataProvider, ITEMS_PER_PAGE) {
			@Override
			protected void populateItem(Item<Order> item) {
				final Order order = orderService.getWithDetails(item.getModelObject());
				final Auto auto = autoService.getWithDetails(order.getAuto());
				order.setAuto(auto);
				item.add(new Label("creationDate"));
				item.add(new Label("id"));

				final WebMarkupContainer itemOrderResult = new WebMarkupContainer("itemOrderResult");
				item.add(itemOrderResult);
				String orderResultClass;
				if (order.getOrderResult() == OrderResult.NONE) {
					orderResultClass = CSS_NONE;
				} else if (order.getOrderResult() == OrderResult.OK) {
					orderResultClass = CSS_OK;
				} else {
					orderResultClass = CSS_CANCELLED;
				}
				itemOrderResult.add(new AttributeModifier("class", new Model<String>(orderResultClass)));
				itemOrderResult.add(new AttributeModifier("title", new ResourceModel("OrderResult." + order.getOrderResult())));

				item.add(new Label("auto.car.regNum", new PropertyModel<String>(auto, "car.regNum")));
				item.add(new Label("driverRating"));
				item.add(new Label("customerRating"));
				item.add(new Label("orderPrice"));

				item.add(new Link<Void>("linkToEdit") {
					@Override
					public void onClick() {
						setResponsePage(new OrderEditPage(order));
					}
				});

			}
		};

		tableBody.add(dataView);

		add(new PagingNavigator("paging", dataView));

	}

	private class OrderDataProvider extends SortableDataProvider<Order, SingularAttribute<Order, ?>> {

		public OrderDataProvider() {
			super();
			setSort(Order_.creationDate, SortOrder.DESCENDING);
		}

		@Override
		public Iterator<? extends Order> iterator(long first, long count) {
			SingularAttribute<Order, ?> sortParam = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(sortParam);
			boolean ascending = SortOrder.ASCENDING.equals(propertySortOrder);
			return orderService.getAll(sortParam, ascending, (int) first, (int) count).iterator();
		}

		@Override
		public long size() {
			return orderService.getCount();
		}

		@Override
		public IModel<Order> model(Order order) {
			return new CompoundPropertyModel<Order>(order);
		}
	}

}
