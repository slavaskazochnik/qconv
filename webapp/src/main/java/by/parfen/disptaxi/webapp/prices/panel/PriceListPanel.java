package by.parfen.disptaxi.webapp.prices.panel;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.Price_;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.services.PriceService;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;
import by.parfen.disptaxi.webapp.prices.PriceEditPage;

public class PriceListPanel extends Panel {

	private static final int ITEMS_PER_PAGE = 15;

	@Inject
	PriceService priceService;

	public PriceListPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		PriceDataProvider priceDataProvider = new PriceDataProvider();

		final WebMarkupContainer tableBody = new WebMarkupContainer("tableBody");

		tableBody.setOutputMarkupId(true);
		add(tableBody);

		DataView<Price> dataView = new DataView<Price>("tableRows", priceDataProvider, ITEMS_PER_PAGE) {
			@Override
			protected void populateItem(Item<Price> item) {
				final IModel<Price> priceModel = item.getModel();
				item.add(new Label("beginDate"));
				item.add(new Label("endDate"));
				item.add(new EnumLabel<CarType>("carType"));
				item.add(new Label("costBefore"));
				item.add(new Label("costKm"));

				item.add(new Link<Void>("linkToEdit") {
					@Override
					public void onClick() {
						setResponsePage(new PriceEditPage(priceModel));
					}

					@Override
					protected void onConfigure() {
						setVisible(BasicAuthenticationSession.get().getUserAppRole() == AppRole.ADMIN_ROLE
								|| BasicAuthenticationSession.get().getUserAppRole() == AppRole.OPERATOR_ROLE);
					}

				});

			}
		};

		tableBody.add(dataView);

		add(new PagingNavigator("paging", dataView));

	}

	private class PriceDataProvider extends SortableDataProvider<Price, SingularAttribute<Price, ?>> {

		public PriceDataProvider() {
			super();
			setSort(Price_.beginDate, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends Price> iterator(long first, long count) {

			SingularAttribute<Price, ?> sortParam = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(sortParam);
			boolean ascending = SortOrder.ASCENDING.equals(propertySortOrder);
			return priceService.getAll(sortParam, ascending, (int) first, (int) count).iterator();
		}

		@Override
		public long size() {
			return priceService.getCount();
		}

		@Override
		public IModel<Price> model(Price userProfile) {
			return new CompoundPropertyModel<Price>(userProfile);
		}
	}

}
