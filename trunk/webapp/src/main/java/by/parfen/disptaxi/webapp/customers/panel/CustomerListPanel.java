package by.parfen.disptaxi.webapp.customers.panel;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Customer_;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.filter.FilterUserProfile;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.webapp.customers.CustomersPage;
import by.parfen.disptaxi.webapp.etc.RatingClass;
import by.parfen.disptaxi.webapp.users.UserProfileEditPage;

public class CustomerListPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int ITEMS_PER_PAGE = 15;

	@Inject
	CustomerService customerService;

	private FilterUserProfile filterUserProfile;

	public FilterUserProfile getFilterUserProfile() {
		return filterUserProfile;
	}

	public void setFilterUserProfile(FilterUserProfile filterUserProfile) {
		this.filterUserProfile = filterUserProfile;
	}

	public CustomerListPanel(String id) {
		this(id, null);
	}

	public CustomerListPanel(String id, IModel<FilterUserProfile> filterUserProfileModel) {
		super(id);
		if (filterUserProfileModel != null) {
			filterUserProfile = filterUserProfileModel.getObject();
		} else {
			filterUserProfile = new FilterUserProfile();
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		CustomerDataProvider customerDataProvider = new CustomerDataProvider();

		Form<FilterUserProfile> filterForm = new Form<FilterUserProfile>("filterForm",
				new CompoundPropertyModel<FilterUserProfile>(filterUserProfile));

		final TextField<String> filterTelNum = new TextField<String>("telNum");
		filterForm.add(filterTelNum);
		add(filterForm);

		SubmitLink submitLink = new SubmitLink("linkToFilter") {
			@Override
			public void onSubmit() {
				setResponsePage(new CustomersPage(new Model<FilterUserProfile>(filterUserProfile)));
			}
		};
		filterForm.add(submitLink);

		final WebMarkupContainer tableBody = new WebMarkupContainer("tableBody");

		tableBody.setOutputMarkupId(true);
		add(tableBody);

		DataView<Customer> dataView = new DataView<Customer>("tableRows", customerDataProvider, ITEMS_PER_PAGE) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<Customer> item) {
				final Customer customer = customerService.getWithDetails(item.getModelObject());
				item.add(new Label("userProfile.firstName"));
				item.add(new Label("userProfile.lastName"));
				item.add(new Label("userProfile.telNum"));
				// item.add(new Label("avgRating"));
				final WebMarkupContainer avgRatingContainter = new WebMarkupContainer("avgRating");
				final int avgRatingPerc = RatingClass.getRatingPercent(customer.getAvgRating());
				avgRatingContainter.add(AttributeModifier.append("style", Model.of("width:" + avgRatingPerc + "%")));
				avgRatingContainter.add(AttributeModifier.append("title", Model.of(customer.getAvgRating())));
				item.add(avgRatingContainter);

				item.add(new Link<Void>("linkToEdit") {
					@Override
					public void onClick() {
						// setResponsePage(new CustomerEditPage(customer));
						setResponsePage(new UserProfileEditPage(new Model<UserProfile>(customer.getUserProfile())) {
							@Override
							protected void onSetResponsePage() {
								// where go to back
								setResponsePage(new CustomersPage());
							}
						});
					}
				});

			}
		};

		tableBody.add(dataView);

		add(new PagingNavigator("paging", dataView));

	}

	private class CustomerDataProvider extends SortableDataProvider<Customer, SingularAttribute<Customer, ?>> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CustomerDataProvider() {
			super();
			setSort(Customer_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends Customer> iterator(long first, long count) {

			SingularAttribute<Customer, ?> sortParam = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(sortParam);
			boolean ascending = SortOrder.ASCENDING.equals(propertySortOrder);
			return customerService.getAllWithDetails(sortParam, ascending, (int) first, (int) count, filterUserProfile)
					.iterator();
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
