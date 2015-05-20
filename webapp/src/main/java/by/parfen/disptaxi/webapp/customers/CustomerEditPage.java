package by.parfen.disptaxi.webapp.customers;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
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
		tfUserId.setEnabled(false);
		form.add(tfUserId);

		final TextField<String> tfFirstName = new TextField<String>("userProfile.firstName");
		tfFirstName.setLabel(new ResourceModel("p.user.firstNameTitle"));
		tfFirstName.add(new PropertyValidator<String>());
		form.add(tfFirstName);

		final TextField<String> tfLastName = new TextField<String>("userProfile.lastName");
		tfLastName.setLabel(new ResourceModel("p.user.lastNameTitle"));
		tfLastName.add(new PropertyValidator<String>());
		form.add(tfLastName);

		final TextField<String> tfTelNum = new TextField<String>("userProfile.telNum");
		tfTelNum.setLabel(new ResourceModel("p.user.telNumTitle"));
		tfTelNum.add(new PropertyValidator<String>());
		form.add(tfTelNum);

		final TextField<String> tfAvgRating = new TextField<String>("avgRating");
		tfAvgRating.setLabel(new ResourceModel("p.user.avgRatingTitle"));
		tfAvgRating.add(new PropertyValidator<String>());
		form.add(tfAvgRating);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				customerService.update(customer);
				CustomersPage page = new CustomersPage();
				// page.error("Custom error");
				// page.info("Custom info");
				// page.warn("Custom warn");
				setResponsePage(page);
			}
		});

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				customerService.delete(customer);
				setResponsePage(new CustomersPage());
			}
		};
		form.add(removeLink);
		removeLink.setDefaultFormProcessing(false);
		removeLink.setVisible(customer.getId() != null);

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new CustomersPage());
			}
		}.setDefaultFormProcessing(false));

		add(form);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.customer.edit.pageTitle");
	}
}
