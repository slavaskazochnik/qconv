package by.parfen.disptaxi.webapp.points.panel;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.PointService;
import by.parfen.disptaxi.webapp.points.PointEditPage;

public class PointInlinePanel extends Panel {

	@Inject
	private PointService pointService;

	private Point point;
	private Street street;

	public PointInlinePanel(String id, final Point point, final Street street) {
		super(id);
		this.point = point;
		this.street = street;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final WebMarkupContainer listItem = new WebMarkupContainer("listItem");
		add(listItem);

		final WebMarkupContainer itemHeader = new WebMarkupContainer("itemHeader");
		listItem.add(itemHeader);

		final String itemName = point.getName();
		itemHeader.add(new Label("itemName", new Model<String>(itemName)));

		final WebMarkupContainer itemDetails = new WebMarkupContainer("itemDetails");
		String pointInfo = getString("p.point.idTitle") + ": " + point.getId();
		itemDetails.add(new Label("pointInfo", pointInfo));
		listItem.add(itemDetails);

		Link<Void> linkToEdit = new Link<Void>("linkToEdit") {
			@Override
			public void onClick() {
				setResponsePage(new PointEditPage(point, street));
			}
		};
		listItem.add(linkToEdit);
	}
}
