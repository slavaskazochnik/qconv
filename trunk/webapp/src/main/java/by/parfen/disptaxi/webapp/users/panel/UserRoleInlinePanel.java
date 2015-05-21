package by.parfen.disptaxi.webapp.users.panel;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.services.UserAccountService;
import by.parfen.disptaxi.services.UserRoleService;

public class UserRoleInlinePanel extends Panel {

	@Inject
	private UserRoleService userRoleService;
	@Inject
	private UserAccountService userAccountService;

	private UserRole userRole;
	private UserAccount userAccount;

	public UserRoleInlinePanel(String id, UserRole userRole) {
		super(id);
		this.userRole = userRole;
		if (this.userRole.getId() != null) {
			userAccount = userAccountService.get(this.userRole.getId());
		}
		if (userAccount == null) {
			userAccount = new UserAccount();
			userAccount.setUserRole(this.userRole);
		}
		userRole.setUserAccount(userAccount);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");
		add(listItem);

		final String userName = userAccount.getName();
		listItem.add(new Label("userName", new Model<String>(userName)));

		String userEmail = getString("p.user.emailTitle") + ": " + userAccount.getEmail();
		listItem.add(new Label("userEmail", userEmail));

		String userAppRoleName = AppRole.CUSTOMER_ROLE.name();
		String userAppRole = getString("AppRole." + userAppRoleName);
		listItem.add(new Label("userAppRole", userAppRole));

		// Link<Void> linkToEdit = new Link<Void>("linkToEdit") {
		// @Override
		// public void onClick() {
		// setResponsePage(new UserRoleEditPage(userRole));
		// }
		// };
		// listItem.add(linkToEdit);
	}
}
