/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package by.parfen.disptaxi.webapp.etc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.string.Strings;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.services.PointService;
import by.parfen.disptaxi.services.StreetService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class ChoicePage extends BaseLayout {

	private String selectedStreetName;

	private Street selectedStreet;

	@Inject
	private CityService cityService;
	@Inject
	private StreetService streetService;
	@Inject
	private PointService pointService;

	private final Map<String, List<String>> pointsMap = new HashMap<String, List<String>>(); // map:F1->F2

	public void setSelectedStreet(Street selectedStreet) {
		this.selectedStreet = selectedStreet;
	}

	/**
	 * Constructor.
	 */
	public ChoicePage() {
		final City city = cityService.get(15L);
		List<Street> streetsList;
		streetsList = streetService.getAllByCity(city);
		for (Street streetItem : streetsList) {
			List<Point> pointsList = pointService.getAllByStreet(streetItem);
			List<String> pointNames = new ArrayList<String>();
			for (Point pointItem : pointsList) {
				pointNames.add(pointItem.getName());
			}
			pointsMap.put(streetItem.getName(), pointNames);
		}
		// pointsMap.put("Лиможа", Arrays.asList("12", "22", "34"));
		// pointsMap.put("Горького", Arrays.asList("2", "4", "45", "13", "78"));
		// pointsMap.put("Ожешко", Arrays.asList("10", "12", "22", "4", "6"));

		IModel<List<? extends String>> makeChoices = new AbstractReadOnlyModel<List<? extends String>>() {
			@Override
			public List<String> getObject() {
				return new ArrayList<String>(pointsMap.keySet());
			}

		};

		IModel<List<? extends String>> modelChoices = new AbstractReadOnlyModel<List<? extends String>>() {
			@Override
			public List<String> getObject() {
				List<String> points = pointsMap.get(selectedStreetName);
				if (points == null) {
					points = Collections.emptyList();
				}
				return points;
			}

		};

		Form<Void> form = new Form<Void>("form");
		add(form);

		final DropDownChoice<String> streets = new DropDownChoice<String>("streets", new PropertyModel<String>(this,
				"selectedStreetName"), makeChoices);

		final DropDownChoice<String> points = new DropDownChoice<String>("points", new Model<String>(), modelChoices);
		points.setOutputMarkupId(true);

		form.add(streets);
		form.add(points);

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		form.add(new AjaxButton("go") {
			@Override
			protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onAfterSubmit(target, form);
				info("Выбранное значение: " + streets.getModelObject() + " " + points.getModelObject());
				target.add(feedback);
			}
		});

		streets.add(new AjaxFormComponentUpdatingBehavior("change") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(points);
			}
		});

		Form<Void> ajaxForm = new Form<Void>("ajaxForm");
		add(ajaxForm);

		final AutoCompleteTextField<String> field = new AutoCompleteTextField<String>("ac", new Model<String>("")) {
			@Override
			protected Iterator<String> getChoices(String input) {
				if (Strings.isEmpty(input)) {
					List<String> emptyList = Collections.emptyList();
					return emptyList.iterator();
				}

				List<String> choices = new ArrayList<String>(10);

				List<Street> streetsList = streetService.getAllByCity(city);

				for (final Street streetItem : streetsList) {
					final String streetName = streetItem.getName();

					if (streetName.toUpperCase().startsWith(input.toUpperCase())) {
						choices.add(streetName);
						if (choices.size() == 10) {
							break;
						}
					}
				}

				return choices.iterator();
			}
		};

		ajaxForm.add(field);

		final Label label = new Label("selectedValue", field.getDefaultModel());
		label.setOutputMarkupId(true);
		ajaxForm.add(label);

		field.add(new AjaxFormSubmitBehavior(ajaxForm, "onchange") {
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				target.add(label);
				List<Street> streetList = streetService.getAllByCityAndName(city, label.getDefaultModelObjectAsString());
				if (streetList.size() == 1) {
					setSelectedStreet(streetList.get(0));
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target) {
			}
		});
	}
}
