package by.parfen.disptaxi.webapp.autos.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.enums.SignActive;
import by.parfen.disptaxi.webapp.neworder.NewOrder;
import by.parfen.disptaxi.webapp.neworder.steps.Step3Confirm;

public class SelectAutoInlinePanel extends Panel {

	private static final String CSS_ONLINE = "online";
	private static final String CSS_OFFLINE = "offline";

	private Auto auto;
	private NewOrder newOrder;

	public SelectAutoInlinePanel(String id, final Auto auto, final NewOrder newOrder) {
		super(id);
		this.auto = auto;
		this.newOrder = newOrder;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");

		final WebMarkupContainer itemHeader = new WebMarkupContainer("itemHeader");
		listItem.add(itemHeader);

		final String autoName = getString("p.auto.idTitle") + ": " + auto.getId().toString();
		itemHeader.add(new Label("itemName", new Model<String>(autoName)));

		final Form<Auto> form = new Form<Auto>("form", new CompoundPropertyModel<Auto>(auto));
		listItem.add(form);

		final WebMarkupContainer itemSignActive = new WebMarkupContainer("itemSignActive");
		form.add(itemSignActive);

		String signActiveClass = CSS_OFFLINE;
		if (auto.getSignActive() == SignActive.YES) {
			signActiveClass = CSS_ONLINE;
		}
		itemSignActive.add(new AttributeModifier("class", new Model<String>(signActiveClass)));

		final WebMarkupContainer carRegNum = new WebMarkupContainer("car.regNum", Model.of(auto.getCar().getRegNum()));
		form.add(carRegNum);

		final WebMarkupContainer driverId = new WebMarkupContainer("driver.id", Model.of(auto.getDriver().getId()));
		form.add(driverId);

		add(listItem);

		Link<Void> linkToSelect = new Link<Void>("linkToSelect") {
			@Override
			public void onClick() {
				// TODO go back to NewOrder.Page2 with saved Route and selected Auto
				newOrder.setAuto(auto);
				setResponsePage(new Step3Confirm(newOrder));
			}
		};
		form.add(linkToSelect);

	}
}
