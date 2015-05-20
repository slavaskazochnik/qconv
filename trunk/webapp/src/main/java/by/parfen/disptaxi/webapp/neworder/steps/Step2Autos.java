package by.parfen.disptaxi.webapp.neworder.steps;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.autos.SelectAutoPanel;
import by.parfen.disptaxi.webapp.neworder.NewOrder;
import by.parfen.disptaxi.webapp.orders.OrdersPage;

public class Step2Autos extends BaseLayout {

	private NewOrder newOrder;

	public Step2Autos(IModel<NewOrder> newOrderModel) {
		Injector.get().inject(this);
		newOrder = newOrderModel.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new SelectAutoPanel("itemsList", new Model<NewOrder>(newOrder)));

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
				setResponsePage(new Step1Route(new Model<NewOrder>(newOrder)));
			}
		}.setDefaultFormProcessing(false));

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.neworder.chooseAutoTitle");
	}
}