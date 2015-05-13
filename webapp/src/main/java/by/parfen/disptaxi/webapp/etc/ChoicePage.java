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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import by.parfen.disptaxi.webapp.BaseLayout;

public class ChoicePage extends BaseLayout {
	private String selectedStreet;

	private final Map<String, List<String>> pointsMap = new HashMap<String, List<String>>(); // map:F1->F2

	/**
	 * @return Currently selected make
	 */
	public String getSelectedStreet() {
		return selectedStreet;
	}

	/**
	 * @param selectedStreet
	 *          The street that is currently selected
	 */
	public void setSelectedStreet(String selectedStreet) {
		this.selectedStreet = selectedStreet;
	}

	/**
	 * Constructor.
	 */
	public ChoicePage() {
		pointsMap.put("Лиможа", Arrays.asList("12", "22", "34"));
		pointsMap.put("Горького", Arrays.asList("2", "4", "45", "13", "78"));
		pointsMap.put("Ожешко", Arrays.asList("10", "12", "22", "4", "6"));

		IModel<List<? extends String>> makeChoices = new AbstractReadOnlyModel<List<? extends String>>() {
			@Override
			public List<String> getObject() {
				return new ArrayList<String>(pointsMap.keySet());
			}

		};

		IModel<List<? extends String>> modelChoices = new AbstractReadOnlyModel<List<? extends String>>() {
			@Override
			public List<String> getObject() {
				List<String> points = pointsMap.get(selectedStreet);
				if (points == null) {
					points = Collections.emptyList();
				}
				return points;
			}

		};

		Form<?> form = new Form("form");
		add(form);

		final DropDownChoice<String> streets = new DropDownChoice<String>("streets", new PropertyModel<String>(this,
				"selectedStreet"), makeChoices);

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
	}
}
