package by.parfen.disptaxi.webapp.prices;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.datamodel.filter.FilterUserProfile;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;
import by.parfen.disptaxi.webapp.prices.panel.PriceListPanel;

public class PricesPage extends BaseLayout {

	private IModel<FilterUserProfile> filterUserProfileModel;

	public PricesPage() {
		this(null);
	}

	public PricesPage(Model<FilterUserProfile> filterUserProfileModel) {
		this.filterUserProfileModel = filterUserProfileModel;
	}

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.prices");
		super.onInitialize();

		add(new PriceListPanel("itemsList"));

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");

		Link<Void> linkToAdd = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				setResponsePage(new PriceEditPage(null));
			}

			@Override
			protected void onConfigure() {
				setVisible(BasicAuthenticationSession.get().getUserAppRole() == AppRole.ADMIN_ROLE
						|| BasicAuthenticationSession.get().getUserAppRole() == AppRole.OPERATOR_ROLE);
			}
		};
		listButtons.add(linkToAdd);

		add(listButtons);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.prices.listTitle");
	}
}