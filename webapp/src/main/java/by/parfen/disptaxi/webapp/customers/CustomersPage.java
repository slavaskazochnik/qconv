package by.parfen.disptaxi.webapp.customers;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.customers.panel.CustomerInlinePanel;

public class CustomersPage extends BaseLayout {

	private static final String DETAILS_PANEL = "detailsPanel";
	private static final String ITEM_PANEL = "itemPanel";

	@Inject
	private CustomerService customerService;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final List<Customer> allCustomers = customerService.getAllWithDetails();
		add(new ListView<Customer>(DETAILS_PANEL, allCustomers) {
			@Override
			protected void populateItem(ListItem<Customer> item) {
				final Customer customer = item.getModelObject();
				item.add(new CustomerInlinePanel(ITEM_PANEL, customer));
			}
		});
	}

}