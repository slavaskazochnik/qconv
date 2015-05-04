package by.parfen.disptaxi.webapp.orders;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class OrdersPage extends BaseLayout {

	private static final String ITEM_ID = "id";
	private static final String DETAILS_PANEL = "details-panel";
	@Inject
	private OrderService orderService;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final List<Order> allOrders = orderService.getAll();
		add(new ListView<Order>(DETAILS_PANEL, allOrders) {
			@Override
			protected void populateItem(ListItem<Order> item) {
				final Order order = item.getModelObject();
				item.add(new Label(ITEM_ID, order.getId()));
			}
		});
	}

}