package by.parfen.disptaxi.webapp.cities;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class CityEditPage extends BaseLayout {

	@Inject
	private CityService cityService;

	public CityEditPage(final City city) {
		super();
		Form<City> form = new Form<City>("inputForm", new CompoundPropertyModel<City>(city));

		final TextField<String> tfId = new TextField<String>("id");
		tfId.setLabel(new ResourceModel("p.city.idTitle"));
		tfId.setEnabled(false);
		form.add(tfId);

		final TextField<String> tfName = new TextField<String>("name");
		tfName.setLabel(new ResourceModel("p.city.nameTitle"));
		tfName.add(new PropertyValidator<String>());
		form.add(tfName);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				cityService.saveOrUpdate(city);
				CitiesPage page = new CitiesPage();
				setResponsePage(page);
			}
		});

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				cityService.delete(city);
				setResponsePage(new CitiesPage());
			}
		};
		form.add(removeLink);
		removeLink.setDefaultFormProcessing(false);
		removeLink.setVisible(city.getId() != null);

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new CitiesPage());
			}
		}.setDefaultFormProcessing(false));

		add(form);
	}
}
