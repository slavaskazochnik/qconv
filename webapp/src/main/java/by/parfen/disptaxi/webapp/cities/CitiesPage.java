package by.parfen.disptaxi.webapp.cities;

import java.util.List;

import javax.inject.Inject;

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
		super.onInitialize();
		final List<City> allCities = cityService.getAll();
		add(new ListView<City>("detailsPanel", allCities) {
			@Override
			protected void populateItem(ListItem<City> item) {
				final City city = item.getModelObject();
				item.add(new CityInlinePanel("itemPanel", city));
			}
		});
	}

}