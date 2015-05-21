package by.parfen.disptaxi.webapp.cars.panel;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.Car_;
import by.parfen.disptaxi.services.CarService;
import by.parfen.disptaxi.webapp.cars.CarEditPage;

public class CarListPanel extends Panel {

	private static final int ITEMS_PER_PAGE = 15;

	@Inject
	CarService carService;

	public CarListPanel(String id) {
		super(id);
		CarDataProvider carDataProvider = new CarDataProvider();

		final WebMarkupContainer tableBody = new WebMarkupContainer("tableBody");

		tableBody.setOutputMarkupId(true);
		add(tableBody);

		DataView<Car> dataView = new DataView<Car>("tableRows", carDataProvider, ITEMS_PER_PAGE) {
			@Override
			protected void populateItem(Item<Car> item) {
				final Car car = item.getModelObject();
				item.add(new Label("regNum"));
				item.add(new Label("carModel"));
				item.add(new Label("seatsQuan"));
				item.add(new Label("childSeatsQuan"));

				item.add(new Link<Void>("linkToEdit") {
					@Override
					public void onClick() {
						setResponsePage(new CarEditPage(car));
					}
				});

			}
		};

		tableBody.add(dataView);

		add(new PagingNavigator("paging", dataView));

	}

	private class CarDataProvider extends SortableDataProvider<Car, SingularAttribute<Car, ?>> {

		public CarDataProvider() {
			super();
			setSort(Car_.regNum, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends Car> iterator(long first, long count) {

			SingularAttribute<Car, ?> sortParam = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(sortParam);
			boolean ascending = SortOrder.ASCENDING.equals(propertySortOrder);
			return carService.getAll(sortParam, ascending, (int) first, (int) count).iterator();
		}

		@Override
		public long size() {
			return carService.getCount();
		}

		@Override
		public IModel<Car> model(Car car) {
			return new CompoundPropertyModel<Car>(car);
		}
	}

}
