package by.parfen.disptaxi.webapp.customers;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.customers.panel.CustomerListPanel;
import by.parfen.disptaxi.webapp.users.UserProfileEditPage;

public class CustomersPage extends BaseLayout {

	// @Inject
	// private CustomerService customerService;

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.customers");
		super.onInitialize();

		add(new CustomerListPanel("itemsList"));

		// final List<Customer> allCustomers = customerService.getAllWithDetails();
		// add(new ListView<Customer>("detailsPanel", allCustomers) {
		// @Override
		// protected void populateItem(ListItem<Customer> item) {
		// final Customer customer = item.getModelObject();
		// item.add(new CustomerInlinePanel("itemPanel", customer));
		// }
		// });

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");
		// Link<Void> linkToAdd = new Link<Void>("linkToAdd") {
		// @Override
		// public void onClick() {
		// final Customer customer = new Customer();
		// setResponsePage(new CustomerEditPage(customer));
		// }
		// };

		Link<Void> linkToAdd = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				// setResponsePage(new CustomerEditPage(customer));
				final Customer customer = new Customer();
				customer.setUserProfile(new UserProfile());
				setResponsePage(new UserProfileEditPage(new Model(customer.getUserProfile())) {
					@Override
					protected void onSetResponsePage() {
						// where go to back
						setResponsePage(new CustomersPage());
					}
				});
			}
		};
		listButtons.add(linkToAdd);

		add(listButtons);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.customers.listTitle");
	}
}