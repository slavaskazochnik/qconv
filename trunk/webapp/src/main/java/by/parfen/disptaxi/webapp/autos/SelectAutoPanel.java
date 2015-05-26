package by.parfen.disptaxi.webapp.autos;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.webapp.autos.panel.SelectAutoInlinePanel;
import by.parfen.disptaxi.webapp.neworder.NewOrder;

public class SelectAutoPanel extends Panel {

	private NewOrder newOrder;

	public SelectAutoPanel(String id, IModel<NewOrder> newOrderModel) {
		super(id);
		newOrder = newOrderModel.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final List<Auto> allAutos = newOrder.getAllAuto(CarType.SEDAN);
		if (allAutos.size() > 0) {
			add(new ListView<Auto>("detailsPanel", allAutos) {
				@Override
				protected void populateItem(ListItem<Auto> item) {
					item.add(new SelectAutoInlinePanel("itemPanel", item.getModel(), new Model<NewOrder>(newOrder)));
				}
			});
		} else {
			WebMarkupContainer detailsPanel = new WebMarkupContainer("detailsPanel");
			add(detailsPanel);
			Label itemPanel = new Label("itemPanel", new ResourceModel("p.cantFindAnyAuto"));
			detailsPanel.add(itemPanel);
			getPage().error(new ResourceModel("p.cantFindAnyAuto").getObject());
		}
		;

	}
}
