package by.parfen.disptaxi.webapp.drivers.panel;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.services.UserProfileService;
import by.parfen.disptaxi.webapp.drivers.DriverEditPage;

public class DriverInlinePanel extends Panel {

	@Inject
	private DriverService driverService;
	@Inject
	private UserProfileService userProfileService;

	private Driver driver;
	private UserProfile userProfile;
	private Boolean showEditButtons;

	public void setDriver(Driver driver) {
		this.driver = driver;
		// this.userProfile = driver.getUserProfile();
		// for LazyInitialization:
		this.userProfile = userProfileService.get(driver.getId());
	}

	public DriverInlinePanel(String id, Long driverId, Boolean showEditButtons) {
		super(id);
		this.showEditButtons = showEditButtons;
		setDriver(driverService.get(driverId));
	}

	public DriverInlinePanel(String id, Long driverId) {
		super(id);
		this.showEditButtons = true;
		setDriver(driverService.get(driverId));
	}

	public DriverInlinePanel(String id, final Driver driver, Boolean showEditButtons) {
		super(id);
		this.showEditButtons = showEditButtons;
		setDriver(driver);
	}

	public DriverInlinePanel(String id, final Driver driver) {
		super(id);
		this.showEditButtons = true;
		setDriver(driver);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");
		add(listItem);

		final WebMarkupContainer itemHeader = new WebMarkupContainer("itemHeader");
		listItem.add(itemHeader);

		final String userDisplayName = userProfile.getFirstName() + " " + userProfile.getLastName();
		itemHeader.add(new Label("itemName", new Model<String>(userDisplayName)));

		final WebMarkupContainer itemDetails = new WebMarkupContainer("itemDetails");

		String driverInfo = getString("p.driver.userIdTitle") + ": " + driver.getId();
		driverInfo = "";
		itemDetails.add(new Label("driverInfo", driverInfo));

		String driverTelNum = getString("p.user.telNumTitle") + ": " + userProfile.getTelNum();
		itemDetails.add(new Label("driverTelNum", driverTelNum));

		String driverAvgRating = getString("p.user.avgRatingTitle") + ": " + driver.getAvgRating();
		itemDetails.add(new Label("driverAvgRating", driverAvgRating));

		listItem.add(itemDetails);
		Link<Void> linkToEdit = new Link<Void>("linkToEdit") {
			@Override
			public void onClick() {
				setResponsePage(new DriverEditPage(driver));
			}
		};
		linkToEdit.setVisible(showEditButtons);
		listItem.add(linkToEdit);
	}
}
