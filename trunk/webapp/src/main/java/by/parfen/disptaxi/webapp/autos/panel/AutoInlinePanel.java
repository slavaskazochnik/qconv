package by.parfen.disptaxi.webapp.autos.panel;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.webapp.autos.AutoEditPage;
import by.parfen.disptaxi.webapp.cars.panel.CarInlinePanel;
import by.parfen.disptaxi.webapp.drivers.panel.DriverInlinePanel;

public class AutoInlinePanel extends Panel {

	@Inject
	private AutoService autoService;

	private Auto auto;
	private Car car;
	private Driver driver;

	public void setAuto(Auto auto) {
		this.auto = auto;
		this.car = auto.getCar();
		this.driver = auto.getDriver();
	}

	public AutoInlinePanel(String id, Long autoId) {
		super(id);
		setAuto(autoService.get(autoId));
	}

	public AutoInlinePanel(String id, final Auto auto) {
		super(id);
		setAuto(auto);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");
		add(listItem);

		final WebMarkupContainer itemHeader = new WebMarkupContainer("itemHeader");
		listItem.add(itemHeader);

		final String autoName = getString("p.auto.idTitle") + ": " + auto.getId().toString();
		itemHeader.add(new Label("itemName", new Model<String>(autoName)));

		final WebMarkupContainer itemDetails = new WebMarkupContainer("itemDetails");

		itemDetails.add(new CarInlinePanel("carInfo", car, false));
		itemDetails.add(new DriverInlinePanel("driverInfo", driver, false));

		listItem.add(itemDetails);

		Link<Void> linkToEdit = new Link<Void>("linkToEdit") {
			@Override
			public void onClick() {
				setResponsePage(new AutoEditPage(auto));
			}
		};
		listItem.add(linkToEdit);
	}
}
