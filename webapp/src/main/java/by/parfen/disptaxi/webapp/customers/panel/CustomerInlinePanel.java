package by.parfen.disptaxi.webapp.customers.panel;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.UserProfileService;

public class CustomerInlinePanel extends Panel {

	private static final String LIST_ITEM = "listItem";
	private static final String ITEM_HEADER = "itemHeader";
	private static final String ITEM_NAME = "itemName";
	private static final String ITEM_DETAILS = "itemDetails";
	private static final String CUSTOMER_INFO = "customerInfo";
	private static final String P_CUSTOMER_USER_ID_TITLE = "p.customer.userIdTitle";

	@Inject
	private CustomerService customerService;
	private Customer customer;
	private UserProfileService userProfileService;
	private UserProfile userProfile;

	public void setCustomer(Customer customer) {
		this.customer = customer;
		this.userProfile = customer.getUserProfile();
	}

	public CustomerInlinePanel(String id, Long customerId) {
		super(id);
		setCustomer(customerService.get(customerId));
	}

	public CustomerInlinePanel(String id, Customer customer) {
		super(id);
		setCustomer(customer);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final WebMarkupContainer listItem = new WebMarkupContainer(LIST_ITEM);
		add(listItem);

		final WebMarkupContainer itemHeader = new WebMarkupContainer(ITEM_HEADER);
		listItem.add(itemHeader);

		final String userDisplayName = userProfile.getFirstName() + " " + userProfile.getLastName();
		itemHeader.add(new Label(ITEM_NAME, new Model(userDisplayName)));

		final WebMarkupContainer itemDetails = new WebMarkupContainer(ITEM_DETAILS);
		String customerInfo = getString(P_CUSTOMER_USER_ID_TITLE) + ": " + customer.getId();
		itemDetails.add(new Label(CUSTOMER_INFO, customerInfo));
		listItem.add(itemDetails);
	}
}
