package by.parfen.disptaxi.webapp.autos;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.autos.panel.AutoInlinePanel;

public class AutosPage extends BaseLayout {

	@Inject
	private AutoService autoService;

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.autos");
		super.onInitialize();
		final List<Auto> allAutos = autoService.getAllWithDetails();
		add(new ListView<Auto>("detailsPanel", allAutos) {
			@Override
			protected void populateItem(ListItem<Auto> item) {
				final Auto auto = item.getModelObject();
				item.add(new AutoInlinePanel("itemPanel", auto));
			}
		});

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");
		Link<Void> linkToEdit = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				final Auto auto = new Auto();
				setResponsePage(new AutoEditPage(auto));
			}
		};
		listButtons.add(linkToEdit);
		add(listButtons);
	}

}