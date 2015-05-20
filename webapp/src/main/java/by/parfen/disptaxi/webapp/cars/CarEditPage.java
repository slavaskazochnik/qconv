package by.parfen.disptaxi.webapp.cars;

import java.util.Arrays;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.services.CarService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class CarEditPage extends BaseLayout {

	@Inject
	private CarService carService;

	public CarEditPage(final Car car) {
		super();
		Form<Car> form = new Form<Car>("inputForm", new CompoundPropertyModel<Car>(car));

		final TextField<String> tfUserId = new TextField<String>("id");
		tfUserId.setEnabled(false);
		form.add(tfUserId);

		final TextField<String> tfRegNum = new TextField<String>("regNum");
		tfRegNum.setLabel(new ResourceModel("p.car.edit.regNumTitle"));
		tfRegNum.add(new PropertyValidator<String>());
		form.add(tfRegNum);

		final TextField<String> tfcarModel = new TextField<String>("carModel");
		tfcarModel.setLabel(new ResourceModel("p.car.edit.carModel"));
		tfcarModel.add(new PropertyValidator<String>());
		form.add(tfcarModel);

		DropDownChoice<CarType> ddcCarType = new DropDownChoice<CarType>("carType", Arrays.asList(CarType.values()),
				new EnumChoiceRenderer<CarType>(this));
		ddcCarType.setLabel(new ResourceModel("p.car.edit.carTypeTitle"));
		ddcCarType.add(new PropertyValidator<CarType>());
		form.add(ddcCarType);

		final TextField<String> tfSeatsQuan = new TextField<String>("seatsQuan");
		tfSeatsQuan.setLabel(new ResourceModel("p.car.edit.seatsQuanTitle"));
		tfSeatsQuan.add(new PropertyValidator<String>());
		form.add(tfSeatsQuan);

		final TextField<String> tfChildSeatsQuan = new TextField<String>("childSeatsQuan");
		tfChildSeatsQuan.setLabel(new ResourceModel("p.car.edit.childSeatsQuanTitle"));
		tfChildSeatsQuan.add(new PropertyValidator<String>());
		form.add(tfChildSeatsQuan);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				carService.update(car);
				CarsPage page = new CarsPage();
				setResponsePage(page);
			}
		});

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				carService.delete(car);
				setResponsePage(new CarsPage());
			}
		};
		form.add(removeLink);
		removeLink.setDefaultFormProcessing(false);
		removeLink.setVisible(car.getId() != null);

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new CarsPage());
			}
		}.setDefaultFormProcessing(false));

		add(form);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.car.edit.pageTitle");
	}
}
