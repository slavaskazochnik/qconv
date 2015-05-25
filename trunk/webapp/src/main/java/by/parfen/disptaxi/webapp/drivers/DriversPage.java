package by.parfen.disptaxi.webapp.drivers;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.drivers.panel.DriverListPanel;
import by.parfen.disptaxi.webapp.users.UserProfileEditPage;

@AuthorizeInstantiation(value = { "ADMIN_ROLE", "OPERATOR_ROLE" })
public class DriversPage extends BaseLayout {

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.drivers");
		super.onInitialize();

		add(new DriverListPanel("itemsList"));

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");

		Link<Void> linkToAdd = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				// setResponsePage(new CustomerEditPage(customer));
				final Driver driver = new Driver();
				driver.setUserProfile(new UserProfile());
				setResponsePage(new UserProfileEditPage(new Model<UserProfile>(driver.getUserProfile())) {
					@Override
					protected void onSetResponsePage() {
						// where go to back
						setResponsePage(new DriversPage());
					}
				});
			}
		};
		listButtons.add(linkToAdd);

		add(listButtons);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.drivers.listTitle");
	}
}