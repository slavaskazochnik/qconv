package by.parfen.disptaxi.webapp.autos;

import java.util.List;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.webapp.autos.panel.SelectAutoInlinePanel;
import by.parfen.disptaxi.webapp.neworder.NewOrder;

public class SelectAutoPanel extends Panel {

	private NewOrder newOrder;

	public SelectAutoPanel(String id, final NewOrder newOrder) {
		super(id);
		this.newOrder = newOrder;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final List<Auto> allAutos = newOrder.getAllAuto(CarType.SEDAN);
		add(new ListView<Auto>("detailsPanel", allAutos) {
			@Override
			protected void populateItem(ListItem<Auto> item) {
				Auto auto = newOrder.getAutoWithDetails((Auto) item.getModelObject());
				item.add(new SelectAutoInlinePanel("itemPanel", auto, newOrder));
			}
		});

	}

}
