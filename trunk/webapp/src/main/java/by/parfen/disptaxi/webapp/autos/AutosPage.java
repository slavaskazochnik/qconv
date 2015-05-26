package by.parfen.disptaxi.webapp.autos;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.datamodel.filter.FilterAuto;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;
import by.parfen.disptaxi.webapp.autos.panel.AutoInlinePanel;

@AuthorizeInstantiation(value = { "ADMIN_ROLE", "OPERATOR_ROLE", "DRIVER_ROLE" })
public class AutosPage extends BaseLayout {

	@Inject
	private AutoService autoService;
	@Inject
	private DriverService driverService;

	private int chooseMode;

	public AutosPage(int chooseMode) {
		this.chooseMode = chooseMode;
		if (chooseMode != 1) {
			this.chooseMode = 0;
		}
	}

	public AutosPage() {
		this(0);
	}

	@Override
	protected void onInitialize() {
		super.setCurrentMenuTitle("p.menu.autos");
		super.onInitialize();

		final FilterAuto filterAuto = new FilterAuto();
		if (BasicAuthenticationSession.get().getUserProfile() != null) {
			if (BasicAuthenticationSession.get().getUserAppRole() == AppRole.DRIVER_ROLE) {
				filterAuto.setDriver(driverService.get(BasicAuthenticationSession.get().getUserProfile().getId()));
			}
		}
		final List<Auto> allAutos = autoService.getAllWithDetails(filterAuto);

		add(new ListView<Auto>("detailsPanel", allAutos) {
			@Override
			protected void populateItem(ListItem<Auto> item) {
				final Auto auto = item.getModelObject();
				item.add(new AutoInlinePanel("itemPanel", auto, chooseMode));
			}
		});

		final WebMarkupContainer listButtons = new WebMarkupContainer("listButtons");
		Link<Void> linkToEdit = new Link<Void>("linkToAdd") {
			@Override
			public void onClick() {
				final Auto auto = new Auto();
				setResponsePage(new AutoEditPage(auto));
			}

			@Override
			protected void onConfigure() {
				setVisible(BasicAuthenticationSession.get().getUserAppRole() == AppRole.ADMIN_ROLE
						|| BasicAuthenticationSession.get().getUserAppRole() == AppRole.OPERATOR_ROLE);
			}

		};
		listButtons.add(linkToEdit);
		add(listButtons);

		linkToEdit.setVisible(chooseMode != 1);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.autos.listTitle");
	}
}