package by.parfen.disptaxi.webapp.customers;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class CustomerEditPage extends BaseLayout {

	@Inject
	private CustomerService customerService;

	public CustomerEditPage(final Customer customer) {
		super();
		Form<Customer> form = new Form<Customer>("inputForm", new CompoundPropertyModel<Customer>(customer));

		final TextField<String> tfUserId = new TextField<String>("id");
		tfUserId.setLabel(new ResourceModel("p.customerEdit.idTitle"));
		form.add(tfUserId);

		final TextField<String> tfFirstName = new TextField<String>("userProfile.firstName");
		tfFirstName.setLabel(new ResourceModel("p.customerEdit.firstNameTitle"));
		tfFirstName.add(new PropertyValidator<String>());
		form.add(tfFirstName);

		final TextField<String> tfLastName = new TextField<String>("userProfile.lastName");
		tfLastName.setLabel(new ResourceModel("p.customerEdit.lastNameTitle"));
		tfFirstName.add(new PropertyValidator<String>());
		form.add(tfLastName);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				customerService.update(customer);
				CustomersPage page = new CustomersPage();
				page.error("Custom error");
				page.info("Custom info");
				page.warn("Custom warn");
				setResponsePage(page);
			}
		});

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new CustomersPage());
			}
		}.setDefaultFormProcessing(false));

		add(form);
	}
}
