package by.parfen.disptaxi.webapp.setup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;
import by.parfen.disptaxi.webapp.home.HomePage;

public class CurrentCityPage extends BaseLayout {

	@Inject
	private CityService cityService;

	private CurrentSettings currentSettings;

	private City selectedCity;

	public CurrentCityPage() {
		super();
		Injector.get().inject(this);

		selectedCity = BasicAuthenticationSession.get().getCity();

		Form<CurrentSettings> form = new Form<CurrentSettings>("inputForm", new CompoundPropertyModel<CurrentSettings>(
				currentSettings));
		add(form);

		List<City> citysList = new ArrayList<City>();
		citysList.addAll(cityService.getAll());
		final DropDownChoice<City> ddcCity = new DropDownChoice<City>("city",
				new PropertyModel<City>(this, "selectedCity"), citysList, new ChoiceRenderer<City>("getName", "id"));
		form.add(ddcCity);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				BasicAuthenticationSession.get().setCity(selectedCity);
				HomePage page = new HomePage();
				setResponsePage(page);
			}
		});

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new HomePage());
			}
		}.setDefaultFormProcessing(false));

	}

}
