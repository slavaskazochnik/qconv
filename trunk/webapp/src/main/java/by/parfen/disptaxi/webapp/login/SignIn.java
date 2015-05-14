package by.parfen.disptaxi.webapp.login;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.parfen.disptaxi.webapp.BaseLayout;

public final class SignIn extends BaseLayout {

	public SignIn() {
		this(null);
	}

	public SignIn(final PageParameters parameters) {
		add(new SignInPanel("signInPanel", false));
	}

}
