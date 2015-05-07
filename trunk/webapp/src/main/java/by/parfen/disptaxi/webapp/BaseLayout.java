package by.parfen.disptaxi.webapp;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.webapp.autos.AutosPage;
import by.parfen.disptaxi.webapp.cities.CitiesPage;
import by.parfen.disptaxi.webapp.customers.CustomersPage;
import by.parfen.disptaxi.webapp.drivers.DriversPage;
import by.parfen.disptaxi.webapp.orders.OrdersPage;

public abstract class BaseLayout extends WebPage {

	private static final String P_MENU_VIEWORDERS = "p.menu.vieworders";
	private static final String P_MENU_AUTOS = "p.menu.autos";
	private static final String P_MENU_DRIVERS = "p.menu.drivers";
	private static final String P_MENU_CUSTOMERS = "p.menu.customers";
	private static final String P_MENU_CITIES = "p.menu.cities";
	private static final String MENU_TEXT = "menuText";
	private static final String MENU_LINK = "menuLink";

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Label("headerTitle", getPageTitle()));

		createNavigationMenu();

		add(new FeedbackPanel("feedbackpanel"));
	}

	private void createNavigationMenu() {
		RepeatingView menuLinkList;
		WebMarkupContainer menuItem;
		Link<?> link;

		menuLinkList = new RepeatingView("menuLinkList");
		add(menuLinkList);

		// #1 Menu element
		menuItem = new WebMarkupContainer(menuLinkList.newChildId());
		link = new Link(MENU_LINK) {
			@Override
			public void onClick() {
				setResponsePage(OrdersPage.class);
			}
		};
		link.add(new Label(MENU_TEXT, new ResourceModel(P_MENU_VIEWORDERS)));
		menuItem.add(link);
		menuLinkList.add(menuItem);

		// #2 Menu element
		menuItem = new WebMarkupContainer(menuLinkList.newChildId());
		link = new Link(MENU_LINK) {
			@Override
			public void onClick() {
				setResponsePage(CustomersPage.class);
			}
		};
		link.add(new Label(MENU_TEXT, new ResourceModel(P_MENU_CUSTOMERS)));
		menuItem.add(link);
		menuLinkList.add(menuItem);

		// #3 Menu element
		menuItem = new WebMarkupContainer(menuLinkList.newChildId());
		link = new Link(MENU_LINK) {
			@Override
			public void onClick() {
				setResponsePage(DriversPage.class);
			}
		};
		link.add(new Label(MENU_TEXT, new ResourceModel(P_MENU_DRIVERS)));
		menuItem.add(link);
		menuLinkList.add(menuItem);

		// #4 Menu element
		menuItem = new WebMarkupContainer(menuLinkList.newChildId());
		link = new Link(MENU_LINK) {
			@Override
			public void onClick() {
				setResponsePage(AutosPage.class);
			}
		};
		link.add(new Label(MENU_TEXT, new ResourceModel(P_MENU_AUTOS)));
		menuItem.add(link);
		menuLinkList.add(menuItem);

		// #5 Menu element
		menuItem = new WebMarkupContainer(menuLinkList.newChildId());
		link = new Link(MENU_LINK) {
			@Override
			public void onClick() {
				setResponsePage(CitiesPage.class);
			}
		};
		link.add(new Label(MENU_TEXT, new ResourceModel(P_MENU_CITIES)));
		menuItem.add(link);
		menuLinkList.add(menuItem);
	}

	protected IModel<String> getPageTitle() {
		return new Model<String>(getClass().getSimpleName());
	}
}
