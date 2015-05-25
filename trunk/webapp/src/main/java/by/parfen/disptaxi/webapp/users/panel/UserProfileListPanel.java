package by.parfen.disptaxi.webapp.users.panel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;
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
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserProfile_;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.services.UserProfileService;
import by.parfen.disptaxi.services.UserRoleService;
import by.parfen.disptaxi.webapp.users.UserProfileEditPage;
import by.parfen.disptaxi.webapp.users.UserProfilesPage;

public class UserProfileListPanel extends Panel {

	private static final int ITEMS_PER_PAGE = 15;

	@Inject
	UserProfileService userProfileService;
	@Inject
	UserRoleService userRoleService;

	public UserProfileListPanel(String id) {
		super(id);
		UserProfileDataProvider userProfileDataProvider = new UserProfileDataProvider();

		final WebMarkupContainer tableBody = new WebMarkupContainer("tableBody");

		tableBody.setOutputMarkupId(true);
		add(tableBody);

		DataView<UserProfile> dataView = new DataView<UserProfile>("tableRows", userProfileDataProvider, ITEMS_PER_PAGE) {
			@Override
			protected void populateItem(Item<UserProfile> item) {
				final UserProfile userProfile = item.getModelObject();
				String userRolesString = getUserRolesString(userProfile);
				item.add(new Label("firstName"));
				item.add(new Label("lastName"));
				item.add(new Label("telNum"));
				item.add(new Label("userRoles", Model.of(userRolesString)));

				item.add(new Link<Void>("linkToEdit") {
					@Override
					public void onClick() {
						// setResponsePage(new UserProfileEditPage(userProfile));
						setResponsePage(new UserProfileEditPage(new Model<UserProfile>(userProfile)) {
							@Override
							protected void onSetResponsePage() {
								// where go to back
								setResponsePage(new UserProfilesPage());
							}
						});
					}
				});

			}
		};

		tableBody.add(dataView);

		add(new PagingNavigator("paging", dataView));

	}

	private String getUserRolesString(UserProfile userProfile) {
		List<UserRole> userRoles = userRoleService.getAllByUserProfile(userProfile);
		String result = "";
		if (userRoles.size() > 0) {
			List<String> userRolesArray = new ArrayList<String>();
			for (UserRole userRole : userRoles) {
				userRolesArray.add(new ResourceModel("AppRole." + userRole.getAppRole()).getObject());
			}
			result = StringUtils.join(userRolesArray, ", ");
		}
		return result;
	}

	private class UserProfileDataProvider extends SortableDataProvider<UserProfile, SingularAttribute<UserProfile, ?>> {

		public UserProfileDataProvider() {
			super();
			setSort(UserProfile_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends UserProfile> iterator(long first, long count) {

			SingularAttribute<UserProfile, ?> sortParam = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(sortParam);
			boolean ascending = SortOrder.ASCENDING.equals(propertySortOrder);
			return userProfileService.getAll(sortParam, ascending, (int) first, (int) count).iterator();
		}

		@Override
		public long size() {
			return userProfileService.getCount();
		}

		@Override
		public IModel<UserProfile> model(UserProfile userProfile) {
			return new CompoundPropertyModel<UserProfile>(userProfile);
		}
	}

}
