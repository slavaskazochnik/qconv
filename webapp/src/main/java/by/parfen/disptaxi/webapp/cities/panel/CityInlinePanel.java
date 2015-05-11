package by.parfen.disptaxi.webapp.cities.panel;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.webapp.cities.CityEditPage;
import by.parfen.disptaxi.webapp.streets.StreetsPage;

public class CityInlinePanel extends Panel {

	@Inject
	private CityService cityService;

	private City city;

	public CityInlinePanel(String id, final City city) {
		super(id);
		this.city = city;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");
		add(listItem);

		final WebMarkupContainer itemHeader = new WebMarkupContainer("itemHeader");
		listItem.add(itemHeader);

		final String itemName = city.getName();
		itemHeader.add(new Label("itemName", new Model<String>(itemName)));

		final WebMarkupContainer itemDetails = new WebMarkupContainer("itemDetails");
		String cityInfo = getString("p.city.idTitle") + ": " + city.getId();
		itemDetails.add(new Label("cityInfo", cityInfo));
		listItem.add(itemDetails);

		Link<Void> linkToStreets = new Link<Void>("linkToStreets") {
			@Override
			public void onClick() {
				setResponsePage(new StreetsPage(city));
			}
		};
		listItem.add(linkToStreets);

		Link<Void> linkToEdit = new Link<Void>("linkToEdit") {
			@Override
			public void onClick() {
				setResponsePage(new CityEditPage(city));
			}
		};
		listItem.add(linkToEdit);
	}
}
