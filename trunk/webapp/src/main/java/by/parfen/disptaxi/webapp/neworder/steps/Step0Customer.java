package by.parfen.disptaxi.webapp.neworder.steps;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.customers.SelectCustomerPanel;
import by.parfen.disptaxi.webapp.neworder.NewOrder;
import by.parfen.disptaxi.webapp.orders.OrdersPage;

public class Step0Customer extends BaseLayout {

	private NewOrder newOrder;

	private Customer customer;

	public Step0Customer(IModel<NewOrder> newOrderModel) {
		newOrder = newOrderModel.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new SelectCustomerPanel("itemsList", new Model<NewOrder>(newOrder)));

		final Form<Void> inputForm = new Form<Void>("inputForm");
		add(inputForm);

		inputForm.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new OrdersPage());
			}
		}.setDefaultFormProcessing(false));

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.neworder.chooseCustomerTitle");
	}
}
