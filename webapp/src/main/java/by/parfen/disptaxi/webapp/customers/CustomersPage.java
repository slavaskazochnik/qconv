package by.parfen.disptaxi.webapp.customers;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.customers.panel.CustomerListPanel;

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
		Link<Void> linkToEdit = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				final Customer customer = new Customer();
				setResponsePage(new CustomerEditPage(customer));
			}
		};
		listButtons.add(linkToEdit);
		add(listButtons);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.customers.listTitle");
	}
}