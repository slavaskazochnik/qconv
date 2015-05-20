package by.parfen.disptaxi.webapp.points;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.PointService;
import by.parfen.disptaxi.webapp.BaseLayout;

public class PointEditPage extends BaseLayout {

	@Inject
	private PointService pointService;

	private City city;

	public PointEditPage(final Point point, final Street street) {
		super();

		Form<Point> form = new Form<Point>("inputForm", new CompoundPropertyModel<Point>(point));

		final TextField<String> tfId = new TextField<String>("id");
		tfId.setLabel(new ResourceModel("p.point.idTitle"));
		tfId.setEnabled(false);
		form.add(tfId);

		final TextField<String> tfCityName = new TextField<String>("cityName",
				new Model<String>(street.getCity().getName()));
		tfCityName.setMarkupId("cityName");
		tfCityName.setEnabled(false);
		form.add(tfCityName);

		final TextField<String> tfStreetName = new TextField<String>("streetName", new Model<String>(street.getName()));
		tfStreetName.setMarkupId("streetName");
		tfStreetName.setEnabled(false);
		form.add(tfStreetName);

		final TextField<String> tfName = new TextField<String>("name");
		tfName.setLabel(new ResourceModel("p.point.nameTitle"));
		tfName.add(new PropertyValidator<String>());
		tfName.setMarkupId("pointName");
		form.add(tfName);

		final TextField<String> tfPointLat = new TextField<String>("pointLat");
		tfPointLat.setLabel(new ResourceModel("p.point.pointLatTitle"));
		tfPointLat.add(new PropertyValidator<String>());
		tfPointLat.setMarkupId("pointLat");
		form.add(tfPointLat);

		final TextField<String> tfPointLng = new TextField<String>("pointLng");
		tfPointLng.setLabel(new ResourceModel("p.point.pointLngTitle"));
		tfPointLng.add(new PropertyValidator<String>());
		tfPointLng.setMarkupId("pointLng");
		form.add(tfPointLng);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				if (point.getId() != null) {
					pointService.update(point);
				} else {
					pointService.create(point, street);
				}
				PointsPage page = new PointsPage(street);
				setResponsePage(page);
			}
		});

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				pointService.delete(point);
				setResponsePage(new PointsPage(street));
			}
		};
		form.add(removeLink);
		removeLink.setDefaultFormProcessing(false);
		removeLink.setVisible(street.getId() != null);

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new PointsPage(street));
			}
		}.setDefaultFormProcessing(false));

		add(form);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.point.edit.pageTitle");
	}
}
