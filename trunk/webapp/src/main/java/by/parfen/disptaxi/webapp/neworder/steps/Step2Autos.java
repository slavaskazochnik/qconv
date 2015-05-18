package by.parfen.disptaxi.webapp.neworder.steps;

import javax.inject.Inject;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.autos.SelectAutoPanel;
import by.parfen.disptaxi.webapp.neworder.NewOrder;
import by.parfen.disptaxi.webapp.orders.OrdersPage;

public class Step2Autos extends BaseLayout {

	@Inject
	private AutoService autoService;

	private static NewOrder newOrder;
	private static Auto auto;

	public Step2Autos(final NewOrder newOrder) {
		Injector.get().inject(this);
		this.newOrder = newOrder;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new SelectAutoPanel("itemsList", newOrder));

		final Form<Void> inputForm = new Form<Void>("inputForm");
		add(inputForm);

		inputForm.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new OrdersPage());
			}
		}.setDefaultFormProcessing(false));

		inputForm.add(new SubmitLink("backLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new Step1Route(newOrder));
			}
		}.setDefaultFormProcessing(false));

	}

}