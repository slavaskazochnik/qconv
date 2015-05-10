package by.parfen.disptaxi.webapp.autos;

import java.util.Arrays;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.enums.SignActive;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.CarService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class AutoEditPage extends BaseLayout {

	@Inject
	private AutoService autoService;
	@Inject
	private CarService carService;

	public AutoEditPage(final Auto auto) {
		super();
		Form<Auto> form = new Form<Auto>("inputForm", new CompoundPropertyModel<Auto>(auto));

		final TextField<String> tfAutoId = new TextField<String>("id");
		tfAutoId.setEnabled(false);
		form.add(tfAutoId);

		DropDownChoice<SignActive> ddcSignActive = new DropDownChoice<SignActive>("signActive", Arrays.asList(SignActive
				.values()), new EnumChoiceRenderer<SignActive>(this));
		ddcSignActive.setLabel(new ResourceModel("p.auto.signActiveTitle"));
		ddcSignActive.add(new PropertyValidator<SignActive>());
		form.add(ddcSignActive);

		// final DropDownChoice<Car> ddcCar = new DropDownChoice<Car>("car.regNum",
		// carService.getAll(),
		// AutoCarChoiseRenderer.INSTANCE);
		// form.add(ddcCar);
		// ddcCar.setLabel(new ResourceModel("p.auto.carTypeTitle"));
		// ddcCar.add(new PropertyValidator<Car>());
		// form.add(ddcCar);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
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
