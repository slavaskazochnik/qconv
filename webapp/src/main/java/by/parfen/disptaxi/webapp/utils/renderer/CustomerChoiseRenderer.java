package by.parfen.disptaxi.webapp.utils.renderer;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

import by.parfen.disptaxi.datamodel.Customer;

public class CustomerChoiseRenderer implements IChoiceRenderer<Customer> {

	public static CustomerChoiseRenderer INSTANCE = new CustomerChoiseRenderer();

	private CustomerChoiseRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Customer customer) {
		return customer.getUserProfile().getUserInfo();
	}

	@Override
	public String getIdValue(Customer customer, int index) {
		return customer.getId().toString();
	}

}