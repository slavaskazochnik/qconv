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

public class CityInlinePanel extends Panel {

	private static final String P_CITY_ID_TITLE = "p.city.idTitle";

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
		String cityInfo = getString(P_CITY_ID_TITLE) + ": " + city.getId();
		itemDetails.add(new Label("cityInfo", cityInfo));
		listItem.add(itemDetails);

		// item.add(new Label("itemMenuPanel", "Put here Edit button"));
		Link<Void> linkToEdit = new Link<Void>("linkToEdit") {
			@Override
			public void onClick() {
				setResponsePage(new CityEditPage(city));
			}
		};
		listItem.add(linkToEdit);
	}
}
