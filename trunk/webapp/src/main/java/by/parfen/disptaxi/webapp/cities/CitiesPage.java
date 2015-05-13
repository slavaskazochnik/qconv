package by.parfen.disptaxi.webapp.cities;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.cities.panel.CityInlinePanel;

public class CitiesPage extends BaseLayout {

	@Inject
	private CityService cityService;

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.cities");
		super.onInitialize();
		final List<City> allCities = cityService.getAll();
		add(new ListView<City>("detailsPanel", allCities) {
			@Override
			protected void populateItem(ListItem<City> item) {
				final City city = item.getModelObject();
				item.add(new CityInlinePanel("itemPanel", city));
			}
		});

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");
		Link<Void> linkToEdit = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				final City city = new City();
				setResponsePage(new CityEditPage(city));
			}
		};
		listButtons.add(linkToEdit);
		add(listButtons);
	}

}