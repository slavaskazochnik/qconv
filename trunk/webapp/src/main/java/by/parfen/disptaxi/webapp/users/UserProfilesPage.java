package by.parfen.disptaxi.webapp.users;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.filter.FilterUserProfile;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.users.panel.UserProfileListPanel;

@AuthorizeInstantiation(value = { "ADMIN_ROLE", "OPERATOR_ROLE" })
public class UserProfilesPage extends BaseLayout {

	private IModel<FilterUserProfile> filterUserProfileModel;

	public UserProfilesPage() {
		this(null);
	}

	public UserProfilesPage(Model<FilterUserProfile> filterUserProfileModel) {
		this.filterUserProfileModel = filterUserProfileModel;
	}

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.users");
		super.onInitialize();

		add(new UserProfileListPanel("itemsList", filterUserProfileModel));

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");

		Link<Void> linkToAdd = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				setResponsePage(new UserProfileEditPage(new Model<UserProfile>(new UserProfile())) {
					@Override
					protected void onSetResponsePage() {
						// where go to back
						setResponsePage(new UserProfilesPage());
					}
				});
			}
		};
		listButtons.add(linkToAdd);

		add(listButtons);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.userProfiles.listTitle");
	}
}