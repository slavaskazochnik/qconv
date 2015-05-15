package by.parfen.disptaxi.webapp.neworder;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.CompoundPropertyModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Route;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.orders.OrderEditPage;
import by.parfen.disptaxi.webapp.orders.OrdersPage;

public class Page3Confirm extends BaseLayout {

	private static Route route;
	private Auto auto;
	private static Order order;

	public Page3Confirm(final Route route, final Auto auto) {
		this.route = route;
		this.auto = auto;
		this.order = new Order();
		route.setOrder(order);
		order.setAuto(this.auto);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final OrderForm orderForm = new OrderForm("inputForm", order);
		add(orderForm);

	}

	static public final class OrderForm extends Form<Order> {

		public OrderForm(String id, final Order order) {
			super(id, new CompoundPropertyModel<Order>(order));

			add(new SubmitLink("cancelLink") {
				@Override
				public void onSubmit() {
					setResponsePage(new OrdersPage());
				}
			}.setDefaultFormProcessing(false));

			add(new SubmitLink("backLink") {
				@Override
				public void onSubmit() {
					setResponsePage(new Page2Autos(route));
				}
			}.setDefaultFormProcessing(false));

			add(new SubmitLink("sumbitLink") {
				@Override
				public void onSubmit() {
					super.onSubmit();
					// final Page2Autos page = new Page2Autos(route);
					setResponsePage(new OrderEditPage(order));
				}
			});
		}

	}
}
