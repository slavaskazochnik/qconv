package by.parfen.disptaxi.webapp.customers;

import java.util.List;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.webapp.customers.panel.SelectCustomerInlinePanel;
import by.parfen.disptaxi.webapp.neworder.NewOrder;

public class SelectCustomerPanel extends Panel {

	private NewOrder newOrder;

	public SelectCustomerPanel(String id, IModel<NewOrder> newOrderModel) {
		super(id);
		newOrder = newOrderModel.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final List<Customer> allCustomers = newOrder.getAllCustomersWithDetails();
		add(new ListView<Customer>("detailsPanel", allCustomers) {
			@Override
			protected void populateItem(ListItem<Customer> item) {
				item.add(new SelectCustomerInlinePanel("itemPanel", item.getModel(), new Model<NewOrder>(newOrder)));
			}
		});

	}

}
