package by.parfen.disptaxi.webapp.orders;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.neworder.NewOrder;
import by.parfen.disptaxi.webapp.neworder.steps.Step0Customer;
import by.parfen.disptaxi.webapp.orders.panel.OrderListPanel;

public class OrdersPage extends BaseLayout {

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.vieworders");
		super.onInitialize();

		add(new OrderListPanel("itemsList"));

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");

		Link<Void> linkToNewOrder = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				// setResponsePage(new Step1Route(new Model<NewOrder>(new NewOrder())));
				setResponsePage(new Step0Customer(new Model<NewOrder>(new NewOrder())));
			}
		};
		listButtons.add(linkToNewOrder);

		add(listButtons);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.orders.listTitle");
	}

}