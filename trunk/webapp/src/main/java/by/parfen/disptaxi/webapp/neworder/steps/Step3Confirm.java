package by.parfen.disptaxi.webapp.neworder.steps;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;

import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.neworder.NewOrder;
import by.parfen.disptaxi.webapp.orders.OrderEditPage;
import by.parfen.disptaxi.webapp.orders.OrdersPage;

public class Step3Confirm extends BaseLayout {

	private NewOrder newOrder;

	public Step3Confirm(final NewOrder newOrder) {
		this.newOrder = newOrder;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final Form<Void> orderForm = new Form<Void>("inputForm");
		add(orderForm);

		orderForm.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new OrdersPage());
			}
		}.setDefaultFormProcessing(false));

		orderForm.add(new SubmitLink("backLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new Step2Autos(newOrder));
			}
		}.setDefaultFormProcessing(false));

		orderForm.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				// TODO ShowOrder
				setResponsePage(new OrderEditPage(null));
			}
		});
	}

}
