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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.webapp.BaseLayout;

public class AutoComplitePage extends BaseLayout {

	private static final int MAX_AUTO_COMPLETE_ELEMENTS = 10;

	private SampleEvent sampleEvent;

	private List<Street> streetsList;
	private List<Point> pointsList;

	/**
	 * Constructor.
	 */
	public AutoComplitePage() {
		Injector.get().inject(this);

		sampleEvent = new SampleEvent();
		// selectedCity = cityService.get(15L);
		sampleEvent.setCityById(60L);
		streetsList = sampleEvent.getStreets();
		// pointsList = sampleEvent.getPointsList();

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		Form<Void> form = new Form<Void>("form");
		add(form);

		final AutoCompleteTextField<String> field = new AutoCompleteTextField<String>("acStreet", new Model<String>("")) {
			@Override
			protected Iterator<String> getChoices(String input) {
				if (Strings.isEmpty(input)) {
					List<String> emptyList = Collections.emptyList();
					return emptyList.iterator();
				}

				List<String> choices = new ArrayList<String>(MAX_AUTO_COMPLETE_ELEMENTS);

				for (final Street streetItem : streetsList) {
					final String streetName = streetItem.getName();

					if (streetName.toUpperCase().startsWith(input.toUpperCase())) {
						choices.add(streetName);
						if (choices.size() == MAX_AUTO_COMPLETE_ELEMENTS) {
							break;
						}
					}
				}

				return choices.iterator();
			}
		};

		final AutoCompleteTextField<String> fieldPoint = new AutoCompleteTextField<String>("acPoint", new Model<String>("")) {
			@Override
			protected Iterator<String> getChoices(String input) {
				if (Strings.isEmpty(input)) {
					List<String> emptyList = Collections.emptyList();
					return emptyList.iterator();
				}

				List<String> choices = new ArrayList<String>(MAX_AUTO_COMPLETE_ELEMENTS);

				for (final Point pointItem : sampleEvent.getPointsList()) {
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

		final Label label = new Label("label", form.getDefaultModel()) {

			@Override
			public void onEvent(IEvent<?> event) {
				Object payload = event.getPayload();
				if (payload instanceof SampleEvent) {
					SampleEvent sampelEvent = (SampleEvent) payload;
					setDefaultModel(Model.of(sampelEvent.getSelectedStreetName() + ", " + sampelEvent.getSelectedPointName()));
					sampelEvent.getTarget().add(this);
				}
			}

		};

		label.setOutputMarkupId(true);
		add(label);

		form.add(field);
		form.add(fieldPoint);

		field.add(new AjaxFormSubmitBehavior(form, "onchange") {
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				sampleEvent.setTarget(target);
				sampleEvent.setSelectedStreetName(field.getDefaultModelObjectAsString());
				sampleEvent.setSelectedPointName("");
				send(getPage(), Broadcast.BREADTH, sampleEvent);
			}

			@Override
			protected void onError(AjaxRequestTarget target) {
			}
		});

		fieldPoint.add(new AjaxFormSubmitBehavior(form, "onchange") {
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				sampleEvent.setTarget(target);
				sampleEvent.setSelectedPointName(fieldPoint.getDefaultModelObjectAsString());
				send(getPage(), Broadcast.BREADTH, sampleEvent);
			}

			@Override
			protected void onError(AjaxRequestTarget target) {
			}
		});
	}
}
