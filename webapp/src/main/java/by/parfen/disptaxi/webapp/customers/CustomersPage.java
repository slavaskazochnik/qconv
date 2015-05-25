package by.parfen.disptaxi.webapp.customers;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.filter.FilterUserProfile;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.customers.panel.CustomerListPanel;
import by.parfen.disptaxi.webapp.users.UserProfileEditPage;

@AuthorizeInstantiation(value = { "ADMIN_ROLE", "OPERATOR_ROLE" })
public class CustomersPage extends BaseLayout {

	private static final long serialVersionUID = 1L;

	private IModel<FilterUserProfile> filterUserProfileModel;
	
	public CustomersPage() {
		this(null);
	}

	public CustomersPage(Model<FilterUserProfile> filterUserProfileModel) {
		this.filterUserProfileModel = filterUserProfileModel;
	}

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.customers");
		super.onInitialize();

		add(new CustomerListPanel("itemsList", filterUserProfileModel));

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");

		Link<Void> linkToAdd = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				// setResponsePage(new CustomerEditPage(customer));
				final Customer customer = new Customer();
				customer.setUserProfile(new UserProfile());
				setResponsePage(new UserProfileEditPage(new Model<UserProfile>(customer.getUserProfile())) {
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