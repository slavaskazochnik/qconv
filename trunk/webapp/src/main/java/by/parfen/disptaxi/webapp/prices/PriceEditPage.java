package by.parfen.disptaxi.webapp.prices;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.services.PriceService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class PriceEditPage extends BaseLayout {

	@Inject
	private PriceService priceService;

	private Price price;

	public PriceEditPage(final IModel<Price> priceModel) {
		super();
		if (priceModel == null) {
			price = new Price();
			price.setBeginDate(new Date());
			price.setCarType(CarType.SEDAN);
		} else {
			price = priceModel.getObject();
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form<Price> form = new Form<Price>("inputForm", new CompoundPropertyModel<Price>(price));

		final TextField<String> tfId = new TextField<String>("id");
		tfId.setEnabled(false);
		form.add(tfId);

		final DateTextField beginDate = new DateTextField("beginDate", new PropertyModel<Date>(price, "beginDate"),
				new StyleDateConverter("M-", true));
		beginDate.add(AttributeModifier.append("title", new ResourceModel("p.price.beginDateTitle")));
		form.add(beginDate);

		final DateTextField endDate = new DateTextField("endDate", new PropertyModel<Date>(price, "endDate"),
				new StyleDateConverter("M-", true));
		endDate.add(AttributeModifier.append("title", new ResourceModel("p.price.endDateTitle")));
		form.add(endDate);

		DatePicker beginDatePicker = getDatePicker();
		DatePicker endDatePicker = getDatePicker();
		beginDate.add(beginDatePicker);
		endDate.add(endDatePicker);

		final TextField<String> costBefore = new TextField<String>("costBefore");
		costBefore.setLabel(new ResourceModel("p.price.edit.costBeforeTitle"));
		costBefore.add(new PropertyValidator<String>());
		form.add(costBefore);

		final TextField<String> costKm = new TextField<String>("costKm");
		costKm.setLabel(new ResourceModel("p.price.edit.costKmTitle"));
		costKm.add(new PropertyValidator<String>());
		form.add(costKm);

		DropDownChoice<CarType> ddcCarType = new DropDownChoice<CarType>("carType", Arrays.asList(CarType.values()),
				new EnumChoiceRenderer<CarType>(this));
		ddcCarType.setLabel(new ResourceModel("p.car.edit.carTypeTitle"));
		ddcCarType.add(new PropertyValidator<CarType>());
		form.add(ddcCarType);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				priceService.update(price);
				setResponsePage(new PricesPage());
			}
		});

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				priceService.delete(price);
				setResponsePage(new PricesPage());
			}
		};
		form.add(removeLink);
		removeLink.setDefaultFormProcessing(false);
		removeLink.setVisible(price.getId() != null);

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new PricesPage());
			}
		}.setDefaultFormProcessing(false));

		add(form);
	}

	private DatePicker getDatePicker() {
		DatePicker datePicker = new DatePicker() {
			@Override
			protected String getAdditionalJavaScript() {
				return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
			}
		};
		datePicker.setShowOnFieldClick(true);
		datePicker.setAutoHide(true);
		return datePicker;
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.car.edit.pageTitle");
	}
}
