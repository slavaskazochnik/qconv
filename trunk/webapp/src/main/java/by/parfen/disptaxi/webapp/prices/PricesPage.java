package by.parfen.disptaxi.webapp.prices;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.filter.FilterUserProfile;
import by.parfen.disptaxi.webapp.BaseLayout;
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

		// final WebMarkupContainer listButtons = new
		// WebMarkupContainer("listButtons");
		//
		// Link<Void> linkToAdd = new Link<Void>("linkToAdd") {
		// @Override
		// public void onClick() {
		// setResponsePage(new PriceEditPage(new Model<Price>(new Price())) {
		// @Override
		// protected void onSetResponsePage() {
		// // where go to back
		// setResponsePage(new PricesPage());
		// }
		// });
		// }
		// };
		// listButtons.add(linkToAdd);
		//
		// add(listButtons);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.prices.listTitle");
	}
}