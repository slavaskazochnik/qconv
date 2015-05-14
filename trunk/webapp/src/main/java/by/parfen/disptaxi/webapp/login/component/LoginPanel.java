package by.parfen.disptaxi.webapp.login.component;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;

import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;
import by.parfen.disptaxi.webapp.app.WicketWebApplication;
import by.parfen.disptaxi.webapp.login.LoginPage;

public class LoginPanel extends Panel {

	public LoginPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Link("loginButton") {
			@Override
			protected void onConfigure() {
				super.onConfigure();
				boolean isLoginPage = LoginPage.class.equals(getPage().getClass());
				boolean isLogged = BasicAuthenticationSession.get().isSignedIn();

				setVisible(!(isLogged || isLoginPage));
			}

			@Override
			public void onClick() {
				setResponsePage(new LoginPage());
			}
		});

		add(new Link("logoutButton") {
			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisible(BasicAuthenticationSession.get().isSignedIn());
			}

			@Override
			public void onClick() {
				final HttpServletRequest servletReq = (HttpServletRequest) getRequest().getContainerRequest();
				servletReq.getSession().invalidate();
				getSession().invalidate();
				getRequestCycle()
						.scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(WicketWebApplication.LOGIN_URL));

			}
		});

		UserAccount user = BasicAuthenticationSession.get().getUser();
		add(new Label("userName", new Model(user != null ? user.getName() : null)));

	}

}
