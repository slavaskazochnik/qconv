package by.parfen.disptaxi.webapp.points;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.PointService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.points.panel.PointInlinePanel;
import by.parfen.disptaxi.webapp.streets.StreetsPage;

@AuthorizeInstantiation(value = { "ADMIN_ROLE", "OPERATOR_ROLE" })
public class PointsPage extends BaseLayout {

	@Inject
	private PointService pointService;

	private Street street;

	public PointsPage(final Street street) {
		this.street = street;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Label("cityName", new Model<String>(street.getCity().getName())));
		add(new Label("streetName", new Model<String>(street.getName())));

		Link<Void> linkToCityStreets = new Link<Void>("linkToCityStreets") {
			@Override
			public void onClick() {
				setResponsePage(new StreetsPage(street.getCity()));
			}
		};
		add(linkToCityStreets);

		final List<Point> allpoints = pointService.getAllByStreet(street);
		add(new ListView<Point>("detailsPanel", allpoints) {
			@Override
			protected void populateItem(ListItem<Point> item) {
				final Point point = item.getModelObject();
				item.add(new PointInlinePanel("itemPanel", point, street));
			}
		});

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");
		Link<Void> linkToEdit = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				final Point point = new Point();
				setResponsePage(new PointEditPage(point, street));
			}
		};
		listButtons.add(linkToEdit);
		add(listButtons);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.points.listTitle");
	}
}