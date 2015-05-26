package by.parfen.disptaxi.webapp.neworder.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.string.Strings;

import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.address.Address;
import by.parfen.disptaxi.webapp.neworder.NewOrder;
import by.parfen.disptaxi.webapp.orders.OrdersPage;

public class Step1Route extends BaseLayout {

	private static final int MAX_AUTO_COMPLETE_ELEMENTS = 10;

	private Address address;

	private NewOrder newOrder;

	private String srcAddress;
	private String dstAddress;

	public Step1Route(IModel<NewOrder> newOrderModel) {
		newOrder = newOrderModel.getObject();
		srcAddress = newOrder.getSrcAddress();
		dstAddress = newOrder.getDstAddress();
		address = new Address();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		setDefaultModel(new CompoundPropertyModel<NewOrder>(newOrder));

		final Form<NewOrder> addressForm = new Form<NewOrder>("addressForm", new CompoundPropertyModel<NewOrder>(newOrder));
		add(addressForm);

		final TextField<String> tfCoutryName = new TextField<String>("countryName", new ResourceModel("p.country.name"));
		tfCoutryName.setMarkupId("countryName");
		tfCoutryName.setEnabled(false);
		addressForm.add(tfCoutryName);

		final TextField<String> tfCityName = new TextField<String>("cityName", new Model<String>(address.getCityName()));
		tfCityName.setMarkupId("cityName");
		tfCityName.setEnabled(false);
		addressForm.add(tfCityName);

		final AutoCompleteTextField<String> tfSrcAddress = new AutoCompleteTextField<String>("srcAddress") {
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
		tfSrcAddress.setMarkupId("srcAddress");
		tfSrcAddress.setLabel(new ResourceModel("p.neworder.srcPointTitle"));
		tfSrcAddress.setRequired(true);

		final AutoCompleteTextField<String> tfDstAddress = new AutoCompleteTextField<String>("dstAddress") {
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
		tfDstAddress.setMarkupId("dstAddress");
		tfDstAddress.setLabel(new ResourceModel("p.neworder.dstPointTitle"));
		tfDstAddress.setRequired(true);

		addressForm.add(tfSrcAddress);
		addressForm.add(tfDstAddress);

		final TextField<String> tfRouteDistance = new TextField<String>("routeDistance");
		tfRouteDistance.setMarkupId("routeDistance");
		addressForm.add(tfRouteDistance);

		final TextField<String> tfRouteDuration = new TextField<String>("routeDuration");
		tfRouteDuration.setMarkupId("routeDuration");
		addressForm.add(tfRouteDuration);

		DropDownChoice<CarType> ddcCarType = new DropDownChoice<CarType>("filterAuto.carType", Arrays.asList(CarType
				.values()), new EnumChoiceRenderer<CarType>(this));
		ddcCarType.add(new PropertyValidator<CarType>());
		addressForm.add(ddcCarType);

		final TextField<String> seatsQuan = new TextField<String>("filterAuto.seatsQuan");
		addressForm.add(seatsQuan);

		final TextField<String> childSeatsQuan = new TextField<String>("filterAuto.childSeatsQuan");
		addressForm.add(childSeatsQuan);

		addressForm.add(new SubmitLink("backLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new Step0Customer(new Model<NewOrder>(newOrder)));
			}
		}.setDefaultFormProcessing(false));

		addressForm.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new OrdersPage());
			}
		}.setDefaultFormProcessing(false));

		addressForm.add(new SubmitLink("sumbitLink") {

			@Override
			public void onSubmit() {
				super.onSubmit();
				final Step2Autos page = new Step2Autos(new Model<NewOrder>(newOrder));
				setResponsePage(page);
			}
		});

		add(addressForm);

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.neworder.createRouteTitle");
	}
}
