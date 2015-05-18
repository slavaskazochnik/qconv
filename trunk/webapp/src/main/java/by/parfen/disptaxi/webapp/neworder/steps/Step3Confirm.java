package by.parfen.disptaxi.webapp.neworder.steps;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.neworder.NewOrder;
import by.parfen.disptaxi.webapp.orders.OrderEditPage;
import by.parfen.disptaxi.webapp.orders.OrdersPage;

public class Step3Confirm extends BaseLayout {

	private NewOrder newOrder;

	public Step3Confirm(final NewOrder newOrder) {
		newOrder.setPrice(newOrder.getPrice(newOrder.getAuto()));
		this.newOrder = newOrder;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final Form<NewOrder> form = new Form<NewOrder>("inputForm");
		add(form);

		form.add(new Label("routeInfo", new Model<String>(newOrder.getRouteInfo())));

		form.add(new Label("autoInfo", new Model<String>(newOrder.getAuto().getCar().getCarInfo())));

		form.add(new Label("priceInfo", new Model<String>(newOrder.getPriceInfo())));

		form.add(new Label("totalInfo", new Model<String>(newOrder.getTotalInfo())));

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new OrdersPage());
			}
		}.setDefaultFormProcessing(false));

		form.add(new SubmitLink("backLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new Step2Autos(newOrder));
			}
		}.setDefaultFormProcessing(false));

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				// TODO Check User==Customer?setCustomer():selectCustomer()
				// Step4Customer
				setResponsePage(new OrderEditPage(null));
			}
		});
	}

}
