package by.parfen.disptaxi.webapp.cars.panel;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.services.CarService;
import by.parfen.disptaxi.webapp.cars.CarEditPage;

public class CarInlinePanel extends Panel {

	@Inject
	private CarService carService;

	private Car car;
	private Boolean showEditButtons;

	public void setCar(Car car) {
		this.car = car;
	}

	public CarInlinePanel(String id, Long carId, Boolean showEditButtons) {
		super(id);
		this.showEditButtons = showEditButtons;
		setCar(carService.get(carId));
	}

	public CarInlinePanel(String id, Long carId) {
		super(id);
		this.showEditButtons = true;
		setCar(carService.get(carId));
	}

	public CarInlinePanel(String id, final Car car, Boolean showEditButtons) {
		super(id);
		this.showEditButtons = showEditButtons;
		setCar(car);
	}

	public CarInlinePanel(String id, final Car car) {
		super(id);
		this.showEditButtons = true;
		setCar(car);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");
		add(listItem);

		final WebMarkupContainer itemHeader = new WebMarkupContainer("itemHeader");
		listItem.add(itemHeader);

		String carDisplayName = car.getCarInfo();
		itemHeader.add(new Label("itemName", new Model<String>(carDisplayName)));

		final WebMarkupContainer itemDetails = new WebMarkupContainer("itemDetails");

		itemDetails.add(new Label("seatsQuan", car.getSeatsQuan()));

		itemDetails.add(new EnumLabel<CarType>("carType", car.getCarType()));

		final Label childSeatsQuanLabel = new Label("childSeatsQuan", car.getChildSeatsQuan());
		itemDetails.add(childSeatsQuanLabel);
		if (car.getChildSeatsQuan() == null || car.getChildSeatsQuan() == 0) {
			childSeatsQuanLabel.setVisible(false);
		}

		listItem.add(itemDetails);
		Link<Void> linkToEdit = new Link<Void>("linkToEdit") {
			@Override
			public void onClick() {
				setResponsePage(new CarEditPage(car));
			}
		};
		linkToEdit.setVisible(showEditButtons);
		listItem.add(linkToEdit);
	}
}
