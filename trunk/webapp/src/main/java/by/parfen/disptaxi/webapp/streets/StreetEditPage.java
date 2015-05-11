package by.parfen.disptaxi.webapp.streets;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.services.StreetService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class StreetEditPage extends BaseLayout {

	@Inject
	private StreetService streetService;
	@Inject
	private CityService cityService;

	// private City city;

	public StreetEditPage(final Street street, final City city) {
		super();

		// city = street.getCity();

		Form<Street> form = new Form<Street>("inputForm", new CompoundPropertyModel<Street>(street));

		final TextField<String> tfId = new TextField<String>("id");
		tfId.setLabel(new ResourceModel("p.street.idTitle"));
		tfId.setEnabled(false);
		form.add(tfId);

		final TextField<String> tfName = new TextField<String>("name");
		tfName.setLabel(new ResourceModel("p.street.nameTitle"));
		tfName.add(new PropertyValidator<String>());
		form.add(tfName);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				if (street.getId() != null) {
					streetService.update(street);
				} else {
					street.setCity(city);
					streetService.create(street, city);
				}
				StreetsPage page = new StreetsPage(city);
				setResponsePage(page);
			}
		});

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				streetService.delete(street);
				setResponsePage(new StreetsPage(city));
			}
		};
		form.add(removeLink);
		removeLink.setDefaultFormProcessing(false);
		removeLink.setVisible(street.getId() != null);

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new StreetsPage(city));
			}
		}.setDefaultFormProcessing(false));

		add(form);
	}
}
