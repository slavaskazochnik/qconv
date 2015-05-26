package by.parfen.disptaxi.webapp.address;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.string.Strings;

import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;

public class AddressLocatorPanel extends Panel {

	private static final int MAX_AUTO_COMPLETE_ELEMENTS = 10;

	private Address address;

	private TextField<String> tfPointLat = null;

	public TextField<String> getTfPointLat() {
		return tfPointLat;
	}

	public TextField<String> getTfPointLng() {
		return tfPointLng;
	}

	private TextField<String> tfPointLng = null;

	public AddressLocatorPanel(final String id, final Address address) {
		super(id);
		this.address = address;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final Form<Address> addressForm = new Form<Address>("addressForm", new CompoundPropertyModel<Address>(address));
		add(addressForm);

		final TextField<String> tfCoutryName = new TextField<String>("countryName", new ResourceModel("p.country.name"));
		tfCoutryName.setMarkupId("countryName");
		tfCoutryName.setEnabled(false);
		addressForm.add(tfCoutryName);

		final TextField<String> tfCityName = new TextField<String>("cityName");
		tfCityName.setMarkupId("cityName");
		tfCityName.setEnabled(false);
		addressForm.add(tfCityName);

		final AutoCompleteTextField<String> streetName = new AutoCompleteTextField<String>("streetName", new Model<String>(
				"")) {
			@Override
			protected Iterator<String> getChoices(String input) {
				if (Strings.isEmpty(input)) {
					List<String> emptyList = Collections.emptyList();
					return emptyList.iterator();
				}

				List<String> choices = new ArrayList<String>(MAX_AUTO_COMPLETE_ELEMENTS);

				for (final Street streetItem : address.getStreetList()) {
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
		streetName.setMarkupId("streetName");

		final AutoCompleteTextField<String> pointName = new AutoCompleteTextField<String>("pointName",
				new Model<String>("")) {
			@Override
			protected Iterator<String> getChoices(String input) {
				if (Strings.isEmpty(input)) {
					List<String> emptyList = Collections.emptyList();
					return emptyList.iterator();
				}

				List<String> choices = new ArrayList<String>(MAX_AUTO_COMPLETE_ELEMENTS);

				for (final Point pointItem : address.getPointList()) {
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
		pointName.setMarkupId("pointName");

		addressForm.add(streetName);
		addressForm.add(pointName);

		// this.tfPointLat = new TextField<String>("pointLat", new
		// Model<String>(address.getPointLat()));
		this.tfPointLat = new TextField<String>("pointLat");
		tfPointLat.setMarkupId("pointLat");
		addressForm.add(tfPointLat);
		// tfPointLat.setEnabled(false);

		// this.tfPointLng = new TextField<String>("pointLng", new
		// Model<String>(address.getPointLng()));
		this.tfPointLng = new TextField<String>("pointLng");
		tfPointLng.setMarkupId("pointLng");
		addressForm.add(tfPointLng);
		// tfPointLng.setEnabled(false);

		// final Label label = new Label("label", addressForm.getDefaultModel()) {
		final Label label = new Label("label", Model.of("")) {

			@Override
			public void onEvent(IEvent<?> event) {
				Object payload = event.getPayload();
				if (payload instanceof Address) {
					Address address = (Address) payload;
					String str = "";
					if (address != null) {
						str = address.getStreetName() + " | " + address.getPointName() + ": <" + address.getStreet().getId()
								+ ">,<" + address.getPoint().getId() + ">";
					}
					setDefaultModel(new Model<String>(str));
				}
			}

		};
		label.setOutputMarkupId(true);
		addressForm.add(label);

		streetName.add(new OnChangeAjaxBehavior() {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(pointName);
				target.add(label);
				final String valueAsString = streetName.getDefaultModelObjectAsString();
				address.setStreetByName(valueAsString);
				send(getPage(), Broadcast.BREADTH, address);
			}
		});

		pointName.add(new OnChangeAjaxBehavior() {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(label);
				// final String valueAsString = ((TextField<String>)
				// getComponent()).getModelObject();
				final String valueAsString = pointName.getDefaultModelObjectAsString();
				address.setPointByName(valueAsString);
				send(getPage(), Broadcast.BREADTH, address);
			}
		});

		// streetName.add(new AjaxFormSubmitBehavior(addressForm, "onchahge") {
		// @Override
		// protected void onSubmit(AjaxRequestTarget target) {
		// target.add(pointName);
		// target.add(label);
		// final String valueAsString = streetName.getDefaultModelObjectAsString();
		// address.setStreetByName(valueAsString);
		// send(getPage(), Broadcast.BREADTH, address);
		// }
		// });
		//
		// pointName.add(new AjaxFormSubmitBehavior(addressForm, "onchahge") {
		// @Override
		// protected void onSubmit(AjaxRequestTarget target) {
		// target.add(label);
		// // final String valueAsString = ((TextField<String>)
		// // getComponent()).getModelObject();
		// final String valueAsString = pointName.getDefaultModelObjectAsString();
		// address.setPointByName(valueAsString);
		// send(getPage(), Broadcast.BREADTH, address);
		// }
		// });

	}
}
