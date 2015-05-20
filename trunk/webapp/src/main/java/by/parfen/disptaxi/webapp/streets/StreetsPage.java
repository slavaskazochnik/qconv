package by.parfen.disptaxi.webapp.streets;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.StreetService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.cities.CitiesPage;
import by.parfen.disptaxi.webapp.streets.panel.StreetInlinePanel;

public class StreetsPage extends BaseLayout {

	@Inject
	private StreetService streetService;

	private City city;

	public StreetsPage(final City city) {
		this.city = city;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Label("cityName", new Model<String>(city.getName())));

		final List<Street> allStreets = streetService.getAllByCity(city);
		add(new ListView<Street>("detailsPanel", allStreets) {
			@Override
			protected void populateItem(ListItem<Street> item) {
				final Street street = item.getModelObject();
				item.add(new StreetInlinePanel("itemPanel", street));
			}
		});

		Link<Void> linkToCities = new Link<Void>("linkToCities") {
			@Override
			public void onClick() {
				setResponsePage(new CitiesPage());
			}
		};
		add(linkToCities);

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");
		Link<Void> linkToEdit = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				final Street street = new Street();
				setResponsePage(new StreetEditPage(street, city));
			}
		};
		listButtons.add(linkToEdit);
		add(listButtons);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.streets.listTitle");
	}
}