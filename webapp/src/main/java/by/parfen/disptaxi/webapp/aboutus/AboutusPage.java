package by.parfen.disptaxi.webapp.aboutus;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.webapp.BaseLayout;

public class AboutusPage extends BaseLayout {

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.aboutus");
		super.onInitialize();
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.aboutus.pageTitle");
	}
}