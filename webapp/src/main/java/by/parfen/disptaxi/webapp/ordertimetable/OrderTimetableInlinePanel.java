package by.parfen.disptaxi.webapp.ordertimetable;

import java.util.Date;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.OrderTimetable;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.services.OrderTimetableService;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;
import by.parfen.disptaxi.webapp.currentorder.CurrentOrderPage;

public class OrderTimetableInlinePanel extends Panel {

	@Inject
	private OrderTimetableService ottService;

	private IModel<Order> orderModel;
	private OrderTimetable orderTimetable;

	public OrderTimetableInlinePanel(String id, IModel<Order> orderModel, IModel<OrderTimetable> orderTimetableModel) {
		super(id, orderTimetableModel);
		this.orderModel = orderModel;
		orderTimetable = orderTimetableModel.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");
		add(listItem);

		final Form<Void> form = new Form<Void>("orderTimtableForm");

		final Date itemDate = orderTimetable.getCreationDate();
		form.add(new Label("itemDate", Model.of(itemDate)));

		final ResourceModel itemInfoModel = new ResourceModel("OrderStatus." + orderTimetable.getOrderStatus());
		form.add(new Label("itemInfo", itemInfoModel));

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			public void onSubmit() {
				ottService.delete(orderTimetable);
				setResponsePage(new CurrentOrderPage(orderModel));
			}
		};
		removeLink.setVisible(BasicAuthenticationSession.get().getUserAppRole() == AppRole.ADMIN_ROLE);
		form.add(removeLink);

		listItem.add(form);

	}
}
