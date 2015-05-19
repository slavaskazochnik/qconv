package by.parfen.disptaxi.webapp.customers.panel;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.webapp.neworder.NewOrder;
import by.parfen.disptaxi.webapp.neworder.steps.Step1Route;

public class SelectCustomerInlinePanel extends Panel {

	private static final String CSS_ONLINE = "online";
	private static final String CSS_OFFLINE = "offline";

	private Customer customer;
	private NewOrder newOrder;

	public SelectCustomerInlinePanel(String id, IModel<Customer> customerModel, IModel<NewOrder> newOrderModel) {
		super(id);
		newOrder = newOrderModel.getObject();
		customer = customerModel.getObject();
		// customer.setUserProfile(newOrder.getUserProfile(customer.getId()));
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final Form<Customer> form = new Form<Customer>("form", new CompoundPropertyModel<Customer>(customer));
		add(form);

		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");

		String itemName = customer.getUserProfile().getUserInfo();
		listItem.add(new Label("itemName", new Model<String>(itemName)));

		listItem.add(new Label("customerInfo", new PropertyModel<String>(customer, "userProfile.firstName")));

		form.add(listItem);

		Link<Void> linkToSelect = new Link<Void>("linkToSelect") {
			@Override
			public void onClick() {
				newOrder.setCustomer(customer);
				setResponsePage(new Step1Route(new Model<NewOrder>(newOrder)));
			}
		};
		listItem.add(linkToSelect);

	}
}
