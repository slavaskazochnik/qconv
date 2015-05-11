package by.parfen.disptaxi.webapp.orders.panel;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.webapp.orders.OrderEditPage;

public class OrderInlinePanel extends Panel {

	@Inject
	private OrderService orderService;

	private Order order;
	private Boolean showEditButtons;

	public void setOrder(Order order) {
		this.order = order;
	}

	public OrderInlinePanel(String id, Long orderId, Boolean showEditButtons) {
		super(id);
		this.showEditButtons = showEditButtons;
		setOrder(orderService.get(orderId));
	}

	public OrderInlinePanel(String id, Long orderId) {
		super(id);
		this.showEditButtons = true;
		setOrder(orderService.get(orderId));
	}

	public OrderInlinePanel(String id, final Order order, Boolean showEditButtons) {
		super(id);
		this.showEditButtons = showEditButtons;
		setOrder(order);
	}

	public OrderInlinePanel(String id, final Order order) {
		super(id);
		this.showEditButtons = true;
		setOrder(order);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");
		add(listItem);

		final WebMarkupContainer itemHeader = new WebMarkupContainer("itemHeader");
		listItem.add(itemHeader);

		String orderDisplayName = order.getId().toString();

		itemHeader.add(new Label("itemName", new Model<String>(orderDisplayName)));

		final WebMarkupContainer itemDetails = new WebMarkupContainer("itemDetails");

		itemDetails.add(new Label("id", new Model<String>(order.getId().toString())));

		listItem.add(itemDetails);
		Link<Void> linkToEdit = new Link<Void>("linkToEdit") {
			@Override
			public void onClick() {
				setResponsePage(new OrderEditPage(order));
			}
		};
		linkToEdit.setVisible(showEditButtons);
		listItem.add(linkToEdit);
	}
}
