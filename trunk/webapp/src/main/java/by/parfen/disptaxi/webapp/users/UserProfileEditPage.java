package by.parfen.disptaxi.webapp.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.services.UserAccountService;
import by.parfen.disptaxi.services.UserProfileService;
import by.parfen.disptaxi.services.UserRoleService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;

public class UserProfileEditPage extends BaseLayout {

	private static final String TEL_MAX_VALUE = "375999999999";
	private static final String TEL_MIN_VALUE = "375000000000";
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private UserRoleService userRoleService;
	@Inject
	private UserAccountService userAccountService;
	@Inject
	private CustomerService customerService;
	@Inject
	private DriverService driverService;

	final UserProfile userProfile;
	UserRole userRole;
	UserAccount userAccount;

	boolean canRemoveProfile;
	boolean canChangeRole;

	public UserProfileEditPage(IModel<UserProfile> userProfileModel) {
		super();
		userProfile = userProfileModel.getObject();

		List<UserRole> userRoleList = new ArrayList<UserRole>();
		if (userProfile != null && userProfile.getId() != null) {
			userRoleList = userRoleService.getAllByUserProfile(userProfile);
		}
		if (userRoleList.size() == 0) {
			userRole = new UserRole();
			userRole.setAppRole(AppRole.CUSTOMER_ROLE);
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

		canRemoveProfile = userProfile.getId() != null
				&& BasicAuthenticationSession.get().getUserAppRole() == AppRole.ADMIN_ROLE;
		canChangeRole = BasicAuthenticationSession.get().getUserAppRole() == AppRole.ADMIN_ROLE
				|| BasicAuthenticationSession.get().getUserAppRole() == AppRole.OPERATOR_ROLE;
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
		tfTelNum.add(RangeValidator.<String> range(TEL_MIN_VALUE, TEL_MAX_VALUE));
		form.add(tfTelNum);

		Form<UserRole> userRoleForm = new Form<UserRole>("userRoleForm", new CompoundPropertyModel<UserRole>(userRole));

		DropDownChoice<AppRole> ddcAppRole = new DropDownChoice<AppRole>("appRole", Arrays.asList(AppRole.values()),
				new EnumChoiceRenderer<AppRole>(this));
		ddcAppRole.setLabel(new ResourceModel("p.userProfile.appRoleTitle"));
		ddcAppRole.add(new PropertyValidator<AppRole>());
		ddcAppRole.isRequired();
		ddcAppRole.setEnabled(canChangeRole);
		userRoleForm.add(ddcAppRole);

		form.add(userRoleForm);

		final TextField<String> userName = new TextField<String>("name",
				new PropertyModel<String>(this.userAccount, "name"));
		userName.setLabel(new ResourceModel("p.userAccount.nameTitle"));
		userName.add(new PropertyValidator<String>());
		form.add(userName);

		final TextField<String> userEmail = new TextField<String>("email", new PropertyModel<String>(this.userAccount,
				"email"));
		userEmail.setLabel(new ResourceModel("p.userAccount.emailTitle"));
		userEmail.add(EmailAddressValidator.getInstance());
		form.add(userEmail);

		final TextField<String> userPassword = new TextField<String>("password", new PropertyModel<String>(
				this.userAccount, "password"));
		userPassword.setLabel(new ResourceModel("p.userAccount.passwordTitle"));
		userPassword.add(StringValidator.lengthBetween(6, 30));
		form.add(userPassword);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				// create user profile
				if (userProfile.getId() == null) {
					userProfileService.saveOrUpdate(userProfile);
				}

				// create user role
				// create role by profile
				if (userRole.getId() == null) {
					userRoleService.create(userRole, userProfile);
				} else {
					userRoleService.update(userRole);
				}

				// create account by role
				if (userAccount.getName() != null && userAccount.getPassw() != null && userAccount.getPassw() != null) {
					userRole.setUserAccount(userAccount);
					userRoleService.update(userRole);
				}

				boolean profileAlreadyUpdated = false;
				if (userRole.getAppRole() == AppRole.CUSTOMER_ROLE) {
					// create record for customers
					Customer customer = customerService.get(userProfile.getId());
					if (customer == null) {
						customer = new Customer();
						customer.setCreationDate(new Date());
						customer.setUserProfile(userProfile);
						userProfile.setCustomer(customer);
						userProfileService.saveOrUpdate(userProfile);
						profileAlreadyUpdated = true;
					}
				} else if (userRole.getAppRole() == AppRole.DRIVER_ROLE) {
					// create record for drivers
					Driver driver = driverService.get(userProfile.getId());
					if (driver == null) {
						driver = new Driver();
						driver.setCreationDate(new Date());
						driver.setUserProfile(userProfile);
						userProfile.setDriver(driver);
						userProfileService.saveOrUpdate(userProfile);
						profileAlreadyUpdated = true;
					}
				}
				if (!profileAlreadyUpdated) {
					userProfileService.saveOrUpdate(userProfile);
				}

				onSetResponsePage();
			}
		});

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				Driver driver = driverService.get(userProfile.getId());
				if (driver != null) {
					driverService.delete(driver);
				}
				Customer customer = customerService.get(userProfile.getId());
				if (customer != null) {
					customerService.delete(customer);
				}
				List<UserRole> userRoleList = userRoleService.getAllByUserProfile(userProfile);
				for (UserRole userRoleItem : userRoleList) {
					UserAccount userAccountItem = userAccountService.get(userRoleItem.getId());
					if (userAccountItem != null && userAccountItem.getId() != null) {
						userAccountService.delete(userAccountItem);
					}
					userRoleService.delete(userRoleItem);
				}
				userProfileService.delete(userProfile);
				onSetResponsePage();
			}
		};
		form.add(removeLink);
		// removeLink.setDefaultFormProcessing(false);
		removeLink.setVisible(canRemoveProfile);

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
