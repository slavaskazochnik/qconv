package by.parfen.disptaxi.webapp.customers.panel;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Customer_;
import by.parfen.disptaxi.services.CustomerService;

public class CustomerListPanel extends Panel {

	private static final int CUSTOMERS_PER_PAGE = 10;

	@Inject
	CustomerService customerService;

	public CustomerListPanel(String id) {
		super(id);
		CustomerDataProvider customerDataProvider = new CustomerDataProvider();

		final WebMarkupContainer tableBody = new WebMarkupContainer("tableBody");

		tableBody.setOutputMarkupId(true);
		add(tableBody);

		DataView<Customer> dataView = new DataView<Customer>("tableRows", customerDataProvider, CUSTOMERS_PER_PAGE) {
			@Override
			protected void populateItem(Item<Customer> item) {
				item.add(new Label("userProfile.firstName"));
				item.add(new Label("userProfile.lastName"));
				item.add(new Label("userProfile.telNum"));
			}
		};

		tableBody.add(dataView);

		add(new PagingNavigator("paging", dataView));

	}

	private class CustomerDataProvider extends SortableDataProvider<Customer, SingularAttribute<Customer, ?>> {

		public CustomerDataProvider() {
			super();
			setSort(Customer_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends Customer> iterator(long first, long count) {

			SingularAttribute<Customer, ?> sortParam = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(sortParam);
			boolean ascending = SortOrder.ASCENDING.equals(propertySortOrder);
			return customerService.getAllWithDetails(sortParam, ascending, (int) first, (int) count).iterator();
		}

		@Override
		public long size() {
			return customerService.getCount();
		}

		@Override
		public IModel<Customer> model(Customer customer) {
			return new CompoundPropertyModel<Customer>(customer);
		}
	}

}
