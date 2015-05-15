package by.parfen.disptaxi.webapp.neworder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Route;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.choosecity.ChooseCity;
import by.parfen.disptaxi.webapp.orders.OrdersPage;

public class Page1Route extends BaseLayout {

	private static final int MAX_AUTO_COMPLETE_ELEMENTS = 10;

	private final ChooseCity chooseCity;
	// private List<Street> streetList;

	private Route route;

	public Page1Route(final Route route) {
		this.route = route;
		chooseCity = new ChooseCity();
		chooseCity.setCurrentCityByName("Гродно");
		this.setCurrentCity(chooseCity.getCurrentCity());
		// streetList = chooseCity.getStreetList();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final Form<Void> addressForm = new Form<Void>("addressForm");
		add(addressForm);

		final RouteForm routeForm = new RouteForm("inputForm", route);
		add(routeForm);

		final TextField<String> tfCityName = new TextField<String>("cityName", new Model<String>(chooseCity
				.getCurrentCity().getName()));
		tfCityName.setMarkupId("cityName");
		tfCityName.setEnabled(false);
		addressForm.add(tfCityName);

		final AutoCompleteTextField<String> field = new AutoCompleteTextField<String>("acStreet", new Model<String>("")) {
			@Override
			protected Iterator<String> getChoices(String input) {
				if (Strings.isEmpty(input)) {
					List<String> emptyList = Collections.emptyList();
					return emptyList.iterator();
				}

				List<String> choices = new ArrayList<String>(MAX_AUTO_COMPLETE_ELEMENTS);

				for (final Street streetItem : chooseCity.getStreetList()) {
					final String streetName = streetItem.getName();

					if (streetName.toUpperCase().contains(input.toUpperCase())) {
						choices.add(streetName);
						if (choices.size() == MAX_AUTO_COMPLETE_ELEMENTS) {
							break;
						}
					}
				}

				return choices.iterator();
			}
		};
		field.setMarkupId("acStreet");

		final AutoCompleteTextField<String> fieldPoint = new AutoCompleteTextField<String>("acPoint", new Model<String>("")) {
			@Override
			protected Iterator<String> getChoices(String input) {
				if (Strings.isEmpty(input)) {
					List<String> emptyList = Collections.emptyList();
					return emptyList.iterator();
				}

				List<String> choices = new ArrayList<String>(MAX_AUTO_COMPLETE_ELEMENTS);

				for (final Point pointItem : chooseCity.getPointList()) {
					final String pointName = pointItem.getName();

					if (pointName.toUpperCase().startsWith(input.toUpperCase())) {
						choices.add(pointName);
						if (choices.size() == MAX_AUTO_COMPLETE_ELEMENTS) {
							break;
						}
					}
				}

				return choices.iterator();
			}
		};
		fieldPoint.setMarkupId("acPoint");

		addressForm.add(field);
		addressForm.add(fieldPoint);

		field.add(new AjaxFormSubmitBehavior(addressForm, "onchange") {
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				target.add(getPage());
				chooseCity.setCurrentStreetByName(field.getDefaultModelObjectAsString());
			}
		});

		fieldPoint.add(new AjaxFormSubmitBehavior(addressForm, "onchange") {
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				target.add(getPage());
				chooseCity.setCurrentPointByName(fieldPoint.getDefaultModelObjectAsString());
				send(getPage(), Broadcast.BREADTH, chooseCity);
			}
		});

		final Label label = new Label("label", addressForm.getDefaultModel()) {

			@Override
			public void onEvent(IEvent<?> event) {
				Object payload = event.getPayload();
				if (payload instanceof ChooseCity) {
					ChooseCity chooseCity = (ChooseCity) payload;
					setDefaultModel(Model.of(chooseCity.getSelectedStreet() + ", " + chooseCity.getSelectedPoint() + ": <"
							+ chooseCity.getCurrentCity().getId().toString() + ">,<" + chooseCity.getCurrentPoint().getId() + ">"));
				}
			}

		};

		addressForm.add(label);

	}

	static public final class RouteForm extends Form<Route> {

		public RouteForm(String id, final Route route) {
			super(id, new CompoundPropertyModel<Route>(route));

			add(new SubmitLink("cancelLink") {
				@Override
				public void onSubmit() {
					setResponsePage(new OrdersPage());
				}
			}.setDefaultFormProcessing(false));

			add(new SubmitLink("sumbitLink") {
				@Override
				public void onSubmit() {
					super.onSubmit();
					final Page2Autos page = new Page2Autos(route);
					setResponsePage(page);
				}
			});
		}

	}
}
