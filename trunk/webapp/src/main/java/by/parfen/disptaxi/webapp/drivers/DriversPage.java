package by.parfen.disptaxi.webapp.drivers;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.drivers.panel.DriverListPanel;

public class DriversPage extends BaseLayout {

	// @Inject
	// private DriverService driverService;

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.drivers");
		super.onInitialize();

		add(new DriverListPanel("itemsList"));

		// final List<Driver> allDrivers = driverService.getAllWithDetails();
		// add(new ListView<Driver>("detailsPanel", allDrivers) {
		// @Override
		// protected void populateItem(ListItem<Driver> item) {
		// final Driver driver = item.getModelObject();
		// item.add(new DriverInlinePanel("itemPanel", driver));
		// }
		// });

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");
		Link<Void> linkToEdit = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				final Driver driver = new Driver();
				setResponsePage(new DriverEditPage(driver));
			}
		};
		listButtons.add(linkToEdit);
		add(listButtons);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.drivers.listTitle");
	}
}