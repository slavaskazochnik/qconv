package by.parfen.disptaxi.webapp.autos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.enums.SignActive;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.CarService;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.address.Address;
import by.parfen.disptaxi.webapp.address.AddressLocatorPanel;

public class AutoEditPage extends BaseLayout {

	@Inject
	private AutoService autoService;
	@Inject
	private CarService carService;
	@Inject
	private DriverService driverService;

	private Car selectedCar;
	private Driver selectedDriver;

	private Address address;

	public Driver getSelectedDriver() {
		return selectedDriver;
	}

	public void setSelectedDriver(Driver selectedDriver) {
		this.selectedDriver = selectedDriver;
	}

	public Car getDefaultCar() {
		return selectedCar;
	}

	public void setDefaultCar(Car selectedCar) {
		this.selectedCar = selectedCar;
	}

	public AutoEditPage(final Auto auto) {
		super();
		address = new Address();
		address.setAddress(auto.getPositionAddress());

		Form<Auto> form = new Form<Auto>("inputForm", new CompoundPropertyModel<Auto>(auto));

		final TextField<String> tfAutoId = new TextField<String>("id");
		tfAutoId.setEnabled(false);
		form.add(tfAutoId);

		DropDownChoice<SignActive> ddcSignActive = new DropDownChoice<SignActive>("signActive", Arrays.asList(SignActive
				.values()), new EnumChoiceRenderer<SignActive>(this));
		ddcSignActive.setLabel(new ResourceModel("p.auto.signActiveTitle"));
		ddcSignActive.add(new PropertyValidator<SignActive>());
		form.add(ddcSignActive);

		List<Car> carsList = new ArrayList<Car>();
		carsList.addAll(carService.getAll());
		// final DropDownChoice<Car> ddcCar = new DropDownChoice<Car>("car",
		// carsList);
		selectedCar = auto.getCar();
		final DropDownChoice<Car> ddcCar = new DropDownChoice<Car>("car", new PropertyModel<Car>(this, "selectedCar"),
				carsList, new ChoiceRenderer<Car>("regNum", "id"));
		form.add(ddcCar);

		List<Driver> driversList = new ArrayList<Driver>();
		driversList.addAll(driverService.getAllWithDetails());
		selectedDriver = auto.getDriver();
		final DropDownChoice<Driver> ddcDriver = new DropDownChoice<Driver>("driver", new PropertyModel<Driver>(this,
				"selectedDriver"), driversList, new ChoiceRenderer<Driver>("userProfile.getUserInfo", "id"));
		form.add(ddcDriver);

		final TextField<String> tfPositionAddress = new TextField<String>("positionAddress");
		tfPositionAddress.setMarkupId("positionAddress");
		form.add(tfPositionAddress);

		final TextField<String> tfPositionLat = new TextField<String>("positionLat");
		tfPositionLat.setMarkupId("positionLat");
		form.add(tfPositionLat);
		tfPositionLat.setEnabled(false);

		final TextField<String> tfPositionLng = new TextField<String>("positionLng");
		tfPositionLng.setMarkupId("positionLng");
		form.add(tfPositionLng);
		tfPositionLng.setEnabled(false);

		address.setPointLat(auto.getPositionLat());
		address.setPointLng(auto.getPositionLng());
		final AddressLocatorPanel addressLocatorPanel = new AddressLocatorPanel("addressPanel", address);
		form.add(addressLocatorPanel);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				auto.setPositionAddress(address.getAddress());
				auto.setPositionLat(address.getPointLat());
				auto.setPositionLng(address.getPointLng());
				// final String lat =
				// addressLocatorPanel.getTfPointLat().getDefaultModelObjectAsString();
				// final String lng =
				// addressLocatorPanel.getTfPointLng().getDefaultModelObjectAsString();
				auto.setCar(selectedCar);
				auto.setDriver(selectedDriver);
				autoService.update(auto);
				AutosPage page = new AutosPage();
				setResponsePage(page);
			}
		});

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				autoService.delete(auto);
				setResponsePage(new AutosPage());
			}
		};
		form.add(removeLink);
		removeLink.setDefaultFormProcessing(false);
		removeLink.setVisible(auto.getId() != null);

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new AutosPage());
			}
		}.setDefaultFormProcessing(false));

		add(form);
	}
}
