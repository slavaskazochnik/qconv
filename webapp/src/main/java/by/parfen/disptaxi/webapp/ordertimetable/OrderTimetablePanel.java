package by.parfen.disptaxi.webapp.ordertimetable;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.OrderTimetable;
import by.parfen.disptaxi.services.OrderTimetableService;

public class OrderTimetablePanel extends Panel {

	@Inject
	private OrderTimetableService orderTimetableService;

	private IModel<Order> orderModel;
	private Order order;

	public OrderTimetablePanel(String id, IModel<Order> orderModel) {
		super(id, orderModel);
		this.orderModel = orderModel;
		order = orderModel.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final List<OrderTimetable> rows = orderTimetableService.getAllByOrder(order);
		add(new ListView<OrderTimetable>("detailsPanel", rows) {
			@Override
			protected void populateItem(ListItem<OrderTimetable> item) {
				// final OrderTimetable orderTimetable = item.getModelObject();
				item.add(new OrderTimetableInlinePanel("itemPanel", orderModel, item.getModel()));
			}
		});
	}
}
