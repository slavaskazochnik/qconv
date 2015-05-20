package by.parfen.disptaxi.webapp.customers.panel;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Customer_;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.webapp.customers.CustomerEditPage;

public class CustomerListPanel extends Panel {

	private static final int ITEMS_PER_PAGE = 15;

	@Inject
	CustomerService customerService;

	public CustomerListPanel(String id) {
		super(id);
		CustomerDataProvider customerDataProvider = new CustomerDataProvider();

		final WebMarkupContainer tableBody = new WebMarkupContainer("tableBody");

		tableBody.setOutputMarkupId(true);
		add(tableBody);

		DataView<Customer> dataView = new DataView<Customer>("tableRows", customerDataProvider, ITEMS_PER_PAGE) {
			@Override
			protected void populateItem(Item<Customer> item) {
				final Customer customer = item.getModelObject();
				item.add(new Label("userProfile.firstName"));
				item.add(new Label("userProfile.lastName"));
				item.add(new Label("userProfile.telNum"));
				item.add(new Label("avgRating"));

				item.add(new Link<Void>("linkToEdit") {
					@Override
					public void onClick() {
						setResponsePage(new CustomerEditPage(customer));
					}
				});

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
