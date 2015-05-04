package by.parfen.disptaxi.webapp.home;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.webapp.BaseLayout;

public class HomePage extends BaseLayout {

	@Override
	protected void onInitialize() {
		super.onInitialize();

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.home.title");
	}

}
