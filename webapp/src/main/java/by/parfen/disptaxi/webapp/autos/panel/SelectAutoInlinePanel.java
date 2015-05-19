package by.parfen.disptaxi.webapp.autos.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.enums.SignActive;
import by.parfen.disptaxi.webapp.neworder.NewOrder;
import by.parfen.disptaxi.webapp.neworder.steps.Step3Confirm;

public class SelectAutoInlinePanel extends Panel {

	private static final String CSS_ONLINE = "online";
	private static final String CSS_OFFLINE = "offline";

	private Auto auto;
	private NewOrder newOrder;

	public SelectAutoInlinePanel(String id, IModel<Auto> autoModel, IModel<NewOrder> newOrderModel) {
		super(id);
		newOrder = newOrderModel.getObject();
		auto = newOrder.getAutoWithDetails(autoModel.getObject());
		auto.getDriver().setUserProfile(newOrder.getUserProfile(this.auto.getDriver().getId()));
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final Form<Auto> form = new Form<Auto>("form", new CompoundPropertyModel<Auto>(auto));
		add(form);

		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");

		String autoName = auto.getCar().getCarInfo();
		listItem.add(new Label("itemName", new Model<String>(autoName)));

		final WebMarkupContainer itemSignActive = new WebMarkupContainer("itemSignActive");
		listItem.add(itemSignActive);

		String signActiveClass = CSS_OFFLINE;
		if (auto.getSignActive() == SignActive.YES) {
			signActiveClass = CSS_ONLINE;
		}
		itemSignActive.add(new AttributeModifier("class", new Model<String>(signActiveClass)));

		listItem.add(new Label("seatsQuan", new PropertyModel<String>(auto, "car.seatsQuan")));
		final Label childSeatsQuan = new Label("childSeatsQuan", new PropertyModel<String>(auto, "car.childSeatsQuan"));
		listItem.add(childSeatsQuan);
		if (auto.getCar().getChildSeatsQuan() == null || auto.getCar().getChildSeatsQuan() == 0) {
			childSeatsQuan.setVisible(false);
		}

		listItem.add(new Label("driverInfo", new PropertyModel<String>(auto, "driver.userProfile.firstName")));

		form.add(listItem);

		Link<Void> linkToSelect = new Link<Void>("linkToSelect") {
			@Override
			public void onClick() {
				newOrder.setAuto(auto);
				setResponsePage(new Step3Confirm(new Model<NewOrder>(newOrder)));
			}
		};
		listItem.add(linkToSelect);

	}
}
