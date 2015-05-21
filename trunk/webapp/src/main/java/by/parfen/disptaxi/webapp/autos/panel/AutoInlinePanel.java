package by.parfen.disptaxi.webapp.autos.panel;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.enums.SignActive;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.webapp.autos.AutoEditPage;
import by.parfen.disptaxi.webapp.cars.panel.CarInlinePanel;
import by.parfen.disptaxi.webapp.drivers.panel.DriverInlinePanel;

public class AutoInlinePanel extends Panel {

	private static final String CSS_ONLINE = "online";
	private static final String CSS_OFFLINE = "offline";

	@Inject
	private AutoService autoService;

	private Auto auto;
	private Car car;
	private Driver driver;
	private int chooseMode;

	public void setAuto(Auto auto, int chooseMode) {
		this.auto = auto;
		this.car = auto.getCar();
		this.driver = auto.getDriver();
		this.chooseMode = chooseMode;
	}

	public AutoInlinePanel(String id, Long autoId, int chooseMode) {
		super(id);
		setAuto(autoService.get(autoId), chooseMode);
	}

	public AutoInlinePanel(String id, final Auto auto, int chooseMode) {
		super(id);
		setAuto(auto, chooseMode);
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

		final WebMarkupContainer itemSignActive = new WebMarkupContainer("itemSignActive");
		itemDetails.add(itemSignActive);
		String signActiveClass = CSS_OFFLINE;
		if (auto.getSignActive() == SignActive.YES) {
			signActiveClass = CSS_ONLINE;
		}
		itemSignActive.add(new AttributeModifier("class", new Model<String>(signActiveClass)));
		itemSignActive.add(new AttributeModifier("title", new ResourceModel("autoSignActive." + auto.getSignActive())));

		listItem.add(itemDetails);

		Link<Void> linkToEdit = new Link<Void>("linkToEdit") {
			@Override
			public void onClick() {
				setResponsePage(new AutoEditPage(auto));
			}
		};
		listItem.add(linkToEdit);

		Link<Void> linkToSelect = new Link<Void>("linkToSelect") {
			@Override
			public void onClick() {
				setResponsePage(new AutoEditPage(auto));
			}
		};
		listItem.add(linkToSelect);

		linkToEdit.setVisible(chooseMode != 1);
		linkToSelect.setVisible(chooseMode == 1);

	}
}
