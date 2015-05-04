package by.parfen.disptaxi.webapp;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

public abstract class BaseLayout extends WebPage {

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Label("headerTitle", getPageTitle()));

		// WebMarkupContainer liContainer = new WebMarkupContainer("menuItem");
		// liContainer.add(new Label("menuItemLink", new
		// ResourceModel("p.menu.orders")));
		// add(liContainer);

		// RepeatingView view = new RepeatingView("menuLinkList");
		// add(view);
		//
		// WebMarkupContainer list = new WebMarkupContainer(view.newChildId());
		// ExternalLink externalLink = new ExternalLink("menuLink", "#orders");
		// externalLink.add(new Label("menuText", new
		// ResourceModel("p.menu.orders")));
		// externalLink.add(new Label("menuText", "Orders"));
		// list.add(externalLink);
		// view.add(list);

		add(new Label("menuItemOrders", new ResourceModel("p.menu.orders")));
		add(new Label("menuItemCustomers", new ResourceModel("p.menu.customers")));
		add(new Label("menuItemAutos", new ResourceModel("p.menu.autos")));
	}

	protected IModel<String> getPageTitle() {
		return new Model<String>(getClass().getSimpleName());
	}
}
