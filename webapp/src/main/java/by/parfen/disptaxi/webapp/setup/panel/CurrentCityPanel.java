package by.parfen.disptaxi.webapp.setup.panel;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.webapp.address.Address;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;
import by.parfen.disptaxi.webapp.setup.CurrentCityPage;

public class CurrentCityPanel extends Panel {

	public CurrentCityPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final Link<Void> linkToCityPage = new Link<Void>("selectCity") {
			@Override
			public void onClick() {
				setResponsePage(new CurrentCityPage());
			}
		};
		add(linkToCityPage);

		City city = BasicAuthenticationSession.get().getCity();
		if (city == null) {
			final Address address = new Address();
			address.setCity(null);
			city = address.getCity();
		}
		String selectCityLabel = new ResourceModel("p.setup.selectCityButtonTitle").getObject();
		if (city != null) {
			selectCityLabel = city.getName();
		}
		linkToCityPage.add(new Label("cityName", Model.of(selectCityLabel)));

	}
}
