package by.parfen.disptaxi.webapp.users;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.services.UserAccountService;
import by.parfen.disptaxi.services.UserProfileService;
import by.parfen.disptaxi.services.UserRoleService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class UserProfileEditPage extends BaseLayout {

	@Inject
	private UserProfileService userProfileService;
	@Inject
	private UserRoleService userRoleService;
	@Inject
	private UserAccountService userAccountService;

	final UserProfile userProfile;
	UserRole userRole;
	UserAccount userAccount;

	public UserProfileEditPage(IModel<UserProfile> userProfileModel) {
		super();
		userProfile = userProfileModel.getObject();

		List<UserRole> userRoleList = userRoleService.getAllByUserProfile(userProfile);
		if (userRoleList.size() == 0) {
			userRole = new UserRole();
		} else {
			// TODO must be multiline
			userRole = userRoleList.get(0);
		}
		userRole.setUserProfile(userProfile);

		if (userRole.getId() == null || userAccountService.get(userRole.getId()) == null) {
			userAccount = new UserAccount();
			userAccount.setUserRole(userRole);
		} else {
			userAccount = userAccountService.get(userRole.getId());
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<UserProfile> form = new Form<UserProfile>("inputForm", new CompoundPropertyModel<UserProfile>(userProfile));

		final TextField<String> tfUserId = new TextField<String>("id");
		tfUserId.setEnabled(false);
		form.add(tfUserId);

		final TextField<String> tfFirstName = new TextField<String>("firstName");
		tfFirstName.setLabel(new ResourceModel("p.user.firstNameTitle"));
		tfFirstName.add(new PropertyValidator<String>());
		form.add(tfFirstName);

		final TextField<String> tfLastName = new TextField<String>("lastName");
		tfLastName.setLabel(new ResourceModel("p.user.lastNameTitle"));
		tfLastName.add(new PropertyValidator<String>());
		form.add(tfLastName);

		final TextField<String> tfTelNum = new TextField<String>("telNum");
		tfTelNum.setLabel(new ResourceModel("p.user.telNumTitle"));
		tfTelNum.add(new PropertyValidator<String>());
		form.add(tfTelNum);

		Form<UserRole> userRoleForm = new Form<UserRole>("userRoleForm", new CompoundPropertyModel<UserRole>(userRole));

		DropDownChoice<AppRole> ddcAppRole = new DropDownChoice<AppRole>("appRole", Arrays.asList(AppRole.values()),
				new EnumChoiceRenderer<AppRole>(this));
		ddcAppRole.setLabel(new ResourceModel("p.userProfile.appRoleTitle"));
		ddcAppRole.add(new PropertyValidator<AppRole>());
		userRoleForm.add(ddcAppRole);

		form.add(userRoleForm);

		Form<UserAccount> userAccountForm = new Form<UserAccount>("userAccountForm",
				new CompoundPropertyModel<UserAccount>(userAccount));

		final TextField<String> userName = new TextField<String>("name");
		userName.setLabel(new ResourceModel("p.userAccount.nameTitle"));
		userName.add(new PropertyValidator<String>());
		userAccountForm.add(userName);

		final TextField<String> userEmail = new TextField<String>("email");
		userEmail.setLabel(new ResourceModel("p.userAccount.emailTitle"));
		userEmail.add(new PropertyValidator<String>());
		userAccountForm.add(userEmail);

		form.add(userAccountForm);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				if (userRole.getId() == null) {
					userRoleService.create(userRole, userProfile);
				} else {
					userRoleService.update(userRole);
				}
				if (userAccount.getId() == null) {
					userAccountService.create(userAccount, userRole);
				} else {
					userAccountService.update(userAccount);
				}
				userProfileService.saveOrUpdate(userProfile);
				onSetResponsePage();
			}
		});

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				userProfileService.delete(userProfile);
				onSetResponsePage();
			}
		};
		form.add(removeLink);
		removeLink.setDefaultFormProcessing(false);
		removeLink.setVisible(userProfile.getId() != null);

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				onSetResponsePage();
			}
		}.setDefaultFormProcessing(false));

		add(form);
	}

	// Hook
	// where go to back
	protected void onSetResponsePage() {
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.userProfile.edit.pageTitle");
	}
}
