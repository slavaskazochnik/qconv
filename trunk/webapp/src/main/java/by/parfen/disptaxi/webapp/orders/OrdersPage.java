package by.parfen.disptaxi.webapp.orders;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.UserAccountService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;
import by.parfen.disptaxi.webapp.neworder.NewOrder;
import by.parfen.disptaxi.webapp.neworder.steps.Step0Customer;
import by.parfen.disptaxi.webapp.neworder.steps.Step1Route;
import by.parfen.disptaxi.webapp.orders.panel.OrderListPanel;

public class OrdersPage extends BaseLayout {

	@Inject
	private UserAccountService userAccountService;
	@Inject
	private CustomerService customerService;

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.vieworders");
		super.onInitialize();

		add(new OrderListPanel("itemsList"));

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");

		Link<Void> linkToNewOrder = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				if (BasicAuthenticationSession.get().getUserAppRole() == AppRole.CUSTOMER_ROLE
						&& BasicAuthenticationSession.get().getUser() != null) {
					final Customer customer = customerService.get(BasicAuthenticationSession.get().getUserProfile().getId());
					final NewOrder newOrder = new NewOrder();
					newOrder.setCustomer(customerService.getWithDetails(customer));
					setResponsePage(new Step1Route(new Model<NewOrder>(newOrder)));
				} else {
					setResponsePage(new Step0Customer(new Model<NewOrder>(new NewOrder())));
				}
			}
		};
		listButtons.add(linkToNewOrder);

		add(listButtons);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.orders.listTitle");
	}

}