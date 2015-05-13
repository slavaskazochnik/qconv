package by.parfen.disptaxi.webapp.cars;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.services.CarService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.cars.panel.CarInlinePanel;

public class CarsPage extends BaseLayout {

	@Inject
	private CarService carService;

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.cars");
		super.onInitialize();
		final List<Car> allCars = carService.getAll();
		add(new ListView<Car>("detailsPanel", allCars) {
			@Override
			protected void populateItem(ListItem<Car> item) {
				final Car car = item.getModelObject();
				item.add(new CarInlinePanel("itemPanel", car));
			}
		});

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");
		Link<Void> linkToEdit = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				final Car car = new Car();
				setResponsePage(new CarEditPage(car));
			}
		};
		listButtons.add(linkToEdit);
		add(listButtons);
	}

}