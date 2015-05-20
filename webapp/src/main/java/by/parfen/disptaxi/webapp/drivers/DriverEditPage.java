package by.parfen.disptaxi.webapp.drivers;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class DriverEditPage extends BaseLayout {

	@Inject
	private DriverService driverService;

	public DriverEditPage(final Driver driver) {
		super();
		Form<Driver> form = new Form<Driver>("inputForm", new CompoundPropertyModel<Driver>(driver));

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
				driverService.update(driver);
				DriversPage page = new DriversPage();
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
				driverService.delete(driver);
				setResponsePage(new DriversPage());
			}
		};
		form.add(removeLink);
		removeLink.setDefaultFormProcessing(false);
		removeLink.setVisible(driver.getId() != null);

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new DriversPage());
			}
		}.setDefaultFormProcessing(false));

		add(form);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.driver.edit.pageTitle");
	}
}
