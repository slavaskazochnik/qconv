package by.parfen.disptaxi.webapp.neworder.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
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
	private List<String> streetNameList, pointNameList, latList, lngList;

	private Route route;

	public Page1Route(final Route route) {
		this.route = route;
		chooseCity = new ChooseCity();
		chooseCity.setCurrentCityByName("Гродно");
		this.setCurrentCity(chooseCity.getCurrentCity());
		// streetList = chooseCity.getStreetList();
		streetNameList = new ArrayList<String>();
		pointNameList = new ArrayList<String>();
		latList = new ArrayList<String>();
		lngList = new ArrayList<String>();
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

		final AutoCompleteTextField<String> fieldStreet = new AutoCompleteTextField<String>("acStreet", new Model<String>(
				"")) {
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
		fieldStreet.setMarkupId("acStreet");

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

		addressForm.add(fieldStreet);
		addressForm.add(fieldPoint);

		final TextField<String> tfPointLat = new TextField<String>("pointLat", new Model<String>(""));
		tfPointLat.setMarkupId("pointLat");
		addressForm.add(tfPointLat);

		final TextField<String> tfPointLng = new TextField<String>("pointLng", new Model<String>(""));
		tfPointLng.setMarkupId("pointLng");
		addressForm.add(tfPointLng);

		final Label label = new Label("label", addressForm.getDefaultModel()) {

			@Override
			public void onEvent(IEvent<?> event) {
				Object payload = event.getPayload();
				if (payload instanceof ChooseCity) {
					ChooseCity chooseCity = (ChooseCity) payload;
					setDefaultModel(Model.of(chooseCity.getSelectedStreet() + ", " + chooseCity.getSelectedPoint() + ": <"
							+ chooseCity.getCurrentStreet().getId().toString() + ">,<" + chooseCity.getCurrentPoint().getId() + ">"));
				}
			}

		};
		label.setOutputMarkupId(true);
		addressForm.add(label);

		fieldStreet.add(new AjaxFormSubmitBehavior(addressForm, "onchange") {
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				target.add(fieldPoint);
				target.add(label);
				chooseCity.setCurrentStreetByName(fieldStreet.getDefaultModelObjectAsString());
				send(getPage(), Broadcast.BREADTH, chooseCity);
			}
		});

		fieldPoint.add(new AjaxFormSubmitBehavior(addressForm, "onchange") {
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				target.add(fieldPoint);
				target.add(label);
				chooseCity.setCurrentPointByName(fieldPoint.getDefaultModelObjectAsString());
				send(getPage(), Broadcast.BREADTH, chooseCity);
			}
		});

		final TextField<String> tfSrcStreetName = new TextField<String>("srcStreetName", new Model<String>(""));
		tfSrcStreetName.setMarkupId("srcStreetName");
		addressForm.add(tfSrcStreetName);
		// TODO Fill fieldsw by Route object!!!!!

		final TextField<String> tfSrcPointName = new TextField<String>("srcPointName", new Model<String>(""));
		tfSrcPointName.setMarkupId("srcPointName");
		addressForm.add(tfSrcPointName);

		final TextField<String> tfSrcPointLat = new TextField<String>("srcPointLat", new Model<String>(""));
		tfSrcPointLat.setMarkupId("srcPointLat");
		addressForm.add(tfSrcPointLat);

		final TextField<String> tfSrcPointLng = new TextField<String>("srcPointLng", new Model<String>(""));
		tfSrcPointLng.setMarkupId("srcPointLng");
		addressForm.add(tfSrcPointLng);

		final TextField<String> tfDstStreetName = new TextField<String>("dstStreetName", new Model<String>(""));
		tfDstStreetName.setMarkupId("dstStreetName");
		addressForm.add(tfDstStreetName);

		final TextField<String> tfDstPointName = new TextField<String>("dstPointName", new Model<String>(""));
		tfDstPointName.setMarkupId("dstPointName");
		addressForm.add(tfDstPointName);

		final TextField<String> tfDstPointLat = new TextField<String>("dstPointLat", new Model<String>(""));
		tfDstPointLat.setMarkupId("dstPointLat");
		addressForm.add(tfDstPointLat);

		final TextField<String> tfDstPointLng = new TextField<String>("dstPointLng", new Model<String>(""));
		tfDstPointLng.setMarkupId("dstPointLng");
		addressForm.add(tfDstPointLng);

		final TextField<String> tfRouteDistance = new TextField<String>("routeDistance", new Model<String>(""));
		tfRouteDistance.setMarkupId("routeDistance");
		addressForm.add(tfRouteDistance);

		final TextField<String> tfRouteDuration = new TextField<String>("routeDuration", new Model<String>(""));
		tfRouteDuration.setMarkupId("routeDuration");
		addressForm.add(tfRouteDuration);

		final TextField<String> tfRoutePrice = new TextField<String>("routePrice", new Model<String>(""));
		tfRoutePrice.setMarkupId("routePrice");
		addressForm.add(tfRoutePrice);

		final AjaxButton ajaxSetSrcPointButton = new AjaxButton("showRoutePriceButton", addressForm) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				streetNameList.add(tfSrcStreetName.getDefaultModelObjectAsString());
				streetNameList.add(tfDstStreetName.getDefaultModelObjectAsString());
				pointNameList.add(tfSrcPointName.getDefaultModelObjectAsString());
				pointNameList.add(tfDstPointName.getDefaultModelObjectAsString());
				latList.add(tfSrcPointLat.getDefaultModelObjectAsString());
				latList.add(tfDstPointLat.getDefaultModelObjectAsString());
				lngList.add(tfSrcPointLng.getDefaultModelObjectAsString());
				lngList.add(tfDstPointLng.getDefaultModelObjectAsString());
				String estlLength = tfRouteDistance.getDefaultModelObjectAsString();
				if (!estlLength.isEmpty()) {
					route.setEstLength(Long.valueOf(estlLength));
				}
				String estTime = tfRouteDuration.getDefaultModelObjectAsString();
				if (!estTime.isEmpty()) {
					route.setEstTime(Long.valueOf(estTime));
				}
				fillRoute();
				tfRoutePrice.setDefaultModelObject("12345");
				target.add(tfRoutePrice);
			}
		};
		addressForm.add(ajaxSetSrcPointButton);

		tfPointLng.setEnabled(false);
		tfPointLat.setEnabled(false);
		tfSrcPointLng.setEnabled(false);
		tfSrcPointLat.setEnabled(false);
		tfDstPointLng.setEnabled(false);
		tfDstPointLat.setEnabled(false);

	}

	private void treatPoint(int index, int size, String streetName, String pointName, String lat, String lng) {
		if (streetName != null) {
			chooseCity.setCurrentStreetByName(streetName);
			Street street = chooseCity.getCurrentStreet();
			chooseCity.setCurrentPointByName(pointName);
			Point point = chooseCity.getCurrentPoint();
			point.setStreet(street);
			if (point.getId() == null) {
				point.setPositionLat(lat);
				point.setPositionLng(lng);
			}
			if (index == 0) {
				route.setSrcPoint(point);
			} else if (index == size - 1) {
				route.setDstPoint(point);
			}
		}
	}

	private void fillRoute() {
		int index;
		index = 0;
		treatPoint(index, streetNameList.size(), streetNameList.get(index), pointNameList.get(index), latList.get(index),
				lngList.get(index));
		index = 1;
		treatPoint(index, streetNameList.size(), streetNameList.get(index), pointNameList.get(index), latList.get(index),
				lngList.get(index));
		chooseCity.searchPrice();
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
					final Page2Autos page = new Page2Autos(((Page1Route) getPage()).route);
					setResponsePage(page);
				}
			});
		}

	}
}
