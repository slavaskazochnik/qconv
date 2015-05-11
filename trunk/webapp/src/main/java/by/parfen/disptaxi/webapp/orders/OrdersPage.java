package by.parfen.disptaxi.webapp.orders;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.orders.panel.OrderInlinePanel;

public class OrdersPage extends BaseLayout {

	@Inject
	private OrderService orderService;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final List<Order> allOrders = orderService.getAll();
		add(new ListView<Order>("detailsPanel", allOrders) {
			@Override
			protected void populateItem(ListItem<Order> item) {
				final Order order = item.getModelObject();
				// item.add(new Label("itemPanel", order.getId()));
				item.add(new OrderInlinePanel("itemPanel", order));
			}
		});

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");
		Link<Void> linkToEdit = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				final Order order = new Order();
				setResponsePage(new OrderEditPage(order));
			}
		};
		listButtons.add(linkToEdit);
		add(listButtons);
	}

}