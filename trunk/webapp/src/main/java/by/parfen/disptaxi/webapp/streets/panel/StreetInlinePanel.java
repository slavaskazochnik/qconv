package by.parfen.disptaxi.webapp.streets.panel;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.StreetService;
import by.parfen.disptaxi.webapp.points.PointsPage;
import by.parfen.disptaxi.webapp.streets.StreetEditPage;

public class StreetInlinePanel extends Panel {

	@Inject
	private StreetService streetService;

	private Street street;
	private City city;

	public StreetInlinePanel(String id, final Street street) {
		super(id);
		this.street = street;
		this.city = street.getCity();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");
		add(listItem);

		final WebMarkupContainer itemHeader = new WebMarkupContainer("itemHeader");
		listItem.add(itemHeader);

		final String itemName = street.getName();
		itemHeader.add(new Label("itemName", new Model<String>(itemName)));

		final WebMarkupContainer itemDetails = new WebMarkupContainer("itemDetails");
		String streetInfo = getString("p.street.idTitle") + ": " + street.getId();
		itemDetails.add(new Label("streetInfo", streetInfo));
		listItem.add(itemDetails);

		Link<Void> linkToPoints = new Link<Void>("linkToPoints") {
			@Override
			public void onClick() {
				setResponsePage(new PointsPage(street));
			}
		};
		listItem.add(linkToPoints);

		Link<Void> linkToEdit = new Link<Void>("linkToEdit") {
			@Override
			public void onClick() {
				setResponsePage(new StreetEditPage(street, city));
			}
		};
		listItem.add(linkToEdit);
	}
}
