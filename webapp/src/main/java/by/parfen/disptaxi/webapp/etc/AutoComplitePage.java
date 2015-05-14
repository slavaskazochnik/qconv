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
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.services.PointService;
import by.parfen.disptaxi.services.StreetService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class AutoComplitePage extends BaseLayout {

	private City selectedCity;
	private String selectedStreetName;
	private Street selectedStreet;
	private Point selectedPoint;

	private List<Street> streetsList;
	private List<Point> pointsList;

	@Inject
	private CityService cityService;
	@Inject
	private StreetService streetService;
	@Inject
	private PointService pointService;

	public void setSelectedStreet(Street selectedStreet) {
		this.selectedStreet = selectedStreet;
		if (selectedStreet != null) {
			pointsList = pointService.getAllByStreet(selectedStreet);
		}
	}

	public void setSelectedStreetName(String selectedStreetName) {
		this.selectedStreetName = selectedStreetName;
		List<Street> streetList = streetService.getAllByCityAndName(selectedCity, selectedStreetName);
		if (streetList.size() == 1) {
			setSelectedStreet(streetList.get(0));
		} else {
			setSelectedStreet(null);
		}
	}

	public List<Street> getStreetsList() {
		return streetsList;
	}

	/**
	 * Constructor.
	 */
	public AutoComplitePage() {
		selectedCity = cityService.get(15L);
		streetsList = streetService.getAllByCity(selectedCity);

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		Form<Void> form = new Form<Void>("form");
		add(form);

		final AutoCompleteTextField<String> field = new AutoCompleteTextField<String>("ac", new Model<String>("")) {
			@Override
			protected Iterator<String> getChoices(String input) {
				if (Strings.isEmpty(input)) {
					List<String> emptyList = Collections.emptyList();
					return emptyList.iterator();
				}

				List<String> choices = new ArrayList<String>(10);

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

		form.add(field);

		final Label label = new Label("selectedValue", field.getDefaultModel());
		label.setOutputMarkupId(true);
		form.add(label);

		field.add(new AjaxFormSubmitBehavior(form, "onchange") {
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				target.add(label);
				setSelectedStreetName(label.getDefaultModelObjectAsString());
			}

			@Override
			protected void onError(AjaxRequestTarget target) {
			}
		});
	}
}
