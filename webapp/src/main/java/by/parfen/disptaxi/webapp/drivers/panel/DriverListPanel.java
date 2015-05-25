package by.parfen.disptaxi.webapp.drivers.panel;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.AttributeModifier;
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
import org.apache.wicket.model.Model;

import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Driver_;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.webapp.drivers.DriversPage;
import by.parfen.disptaxi.webapp.etc.RatingClass;
import by.parfen.disptaxi.webapp.users.UserProfileEditPage;

public class DriverListPanel extends Panel {

	private static final int ITEMS_PER_PAGE = 15;

	@Inject
	DriverService driverService;

	public DriverListPanel(String id) {
		super(id);
		DriverDataProvider driverDataProvider = new DriverDataProvider();

		final WebMarkupContainer tableBody = new WebMarkupContainer("tableBody");

		tableBody.setOutputMarkupId(true);
		add(tableBody);

		DataView<Driver> dataView = new DataView<Driver>("tableRows", driverDataProvider, ITEMS_PER_PAGE) {
			@Override
			protected void populateItem(Item<Driver> item) {
				final Driver driver = item.getModelObject();
				item.add(new Label("userProfile.firstName"));
				item.add(new Label("userProfile.lastName"));
				item.add(new Label("userProfile.telNum"));
				// item.add(new Label("avgRating"));
				final WebMarkupContainer avgRatingContainter = new WebMarkupContainer("avgRating");
				final int avgRatingPerc = RatingClass.getRatingPercent(driver.getAvgRating());
				avgRatingContainter.add(AttributeModifier.append("style", Model.of("width:" + avgRatingPerc + "%")));
				avgRatingContainter.add(AttributeModifier.append("title", Model.of(driver.getAvgRating())));
				item.add(avgRatingContainter);

				item.add(new Link<Void>("linkToEdit") {
					@Override
					public void onClick() {
						// setResponsePage(new DriverEditPage(driver));
						setResponsePage(new UserProfileEditPage(new Model<UserProfile>(driver.getUserProfile())) {
							@Override
							protected void onSetResponsePage() {
								// where go to back
								setResponsePage(new DriversPage());
							}
						});
					}
				});

			}
		};

		tableBody.add(dataView);

		add(new PagingNavigator("paging", dataView));

	}

	private class DriverDataProvider extends SortableDataProvider<Driver, SingularAttribute<Driver, ?>> {

		public DriverDataProvider() {
			super();
			setSort(Driver_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends Driver> iterator(long first, long count) {

			SingularAttribute<Driver, ?> sortParam = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(sortParam);
			boolean ascending = SortOrder.ASCENDING.equals(propertySortOrder);
			return driverService.getAllWithDetails(sortParam, ascending, (int) first, (int) count).iterator();
		}

		@Override
		public long size() {
			return driverService.getCount();
		}

		@Override
		public IModel<Driver> model(Driver driver) {
			return new CompoundPropertyModel<Driver>(driver);
		}
	}

}
