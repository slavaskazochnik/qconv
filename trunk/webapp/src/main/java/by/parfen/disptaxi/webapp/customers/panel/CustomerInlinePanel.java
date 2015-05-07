package by.parfen.disptaxi.webapp.customers.panel;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.webapp.customers.CustomerEditPage;

public class CustomerInlinePanel extends Panel {

	private static final String P_CUSTOMER_USER_ID_TITLE = "p.customer.userIdTitle";

	@Inject
	private CustomerService customerService;

	private Customer customer;
	private UserProfile userProfile;

	public void setCustomer(Customer customer) {
		this.customer = customer;
		this.userProfile = customer.getUserProfile();
	}

	public CustomerInlinePanel(String id, Long customerId) {
		super(id);
		setCustomer(customerService.get(customerId));
	}

	public CustomerInlinePanel(String id, final Customer customer) {
		super(id);
		setCustomer(customer);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");
		add(listItem);

		final WebMarkupContainer itemHeader = new WebMarkupContainer("itemHeader");
		listItem.add(itemHeader);

		final String userDisplayName = userProfile.getFirstName() + " " + userProfile.getLastName();
		itemHeader.add(new Label("itemName", new Model<String>(userDisplayName)));

		final WebMarkupContainer itemDetails = new WebMarkupContainer("itemDetails");
		String customerInfo = getString(P_CUSTOMER_USER_ID_TITLE) + ": " + customer.getId();
		itemDetails.add(new Label("customerInfo", customerInfo));
		listItem.add(itemDetails);

		Link<Void> linkToEdit = new Link<Void>("linkToEdit") {
			@Override
			public void onClick() {
				setResponsePage(new CustomerEditPage(customer));
			}
		};
		listItem.add(linkToEdit);
	}
}
