package by.parfen.disptaxi.webapp.login;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.string.Strings;

import by.parfen.disptaxi.webapp.BaseLayout;

public class LoginPage extends BaseLayout {

	public static final String ID_FORM = "form";
	public static final String ID_PASSWORD = "password";
	public static final String ID_USERNAME = "username";

	private String username;
	private String password;

	@Override
	protected void onInitialize() {
		super.onInitialize();

		// if already logged then should not see login page at all
		if (AuthenticatedWebSession.get().isSignedIn()) {
			setResponsePage(Application.get().getHomePage());
		}
		final Form<Void> form = new Form<Void>(ID_FORM) {

			@Override
			protected void onSubmit() {
				if (Strings.isEmpty(username) || Strings.isEmpty(password)) {
					return;
				}
				final boolean authResult = AuthenticatedWebSession.get().signIn(username, password);
				if (authResult) {
					// continueToOriginalDestination();
					setResponsePage(Application.get().getHomePage());
				} else {
					error(new ResourceModel("p.login.authenticationFailed").getObject());
				}
			}
		};

		form.setDefaultModel(new CompoundPropertyModel<LoginPage>(this));
		final RequiredTextField<String> tfUserName = new RequiredTextField<String>(ID_USERNAME);
		tfUserName.setLabel(new ResourceModel("p.login.username"));
		form.add(tfUserName);

		final PasswordTextField tfPassword = new PasswordTextField(ID_PASSWORD);
		tfPassword.setLabel(new ResourceModel("p.login.password"));
		form.add(tfPassword);
		add(form);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.login.pageTitle");
	}

}
