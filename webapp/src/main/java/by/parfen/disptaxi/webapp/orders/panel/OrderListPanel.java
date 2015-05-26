package by.parfen.disptaxi.webapp.orders.panel;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Order_;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.datamodel.filter.FilterOrder;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;
import by.parfen.disptaxi.webapp.currentorder.CurrentOrderPage;
import by.parfen.disptaxi.webapp.etc.RatingClass;
import by.parfen.disptaxi.webapp.orders.OrdersPage;

public class OrderListPanel extends Panel {

	private static final int ITEMS_PER_PAGE = 7;
	private static final String CSS_NONE = "order-result-none";
	private static final String CSS_OK = "order-result-ok";
	private static final String CSS_CANCELLED = "order-result-cancelled";

	@Inject
	private OrderService orderService;
	@Inject
	private AutoService autoService;
	@Inject
	private DriverService driverService;
	@Inject
	private CustomerService customerService;

	private FilterOrder filterOrder;

	public OrderListPanel(String id) {
		this(id, new Model<FilterOrder>(new FilterOrder()));
	}

	public OrderListPanel(String id, IModel<FilterOrder> filterOrderModel) {
		super(id);
		filterOrder = filterOrderModel.getObject();
		// filterOrder = new FilterOrder();
		if (BasicAuthenticationSession.get().getUserProfile() != null) {
			if (BasicAuthenticationSession.get().getUserAppRole() == AppRole.DRIVER_ROLE) {
				filterOrder.setDriver(driverService.get(BasicAuthenticationSession.get().getUserProfile().getId()));
			} else if (BasicAuthenticationSession.get().getUserAppRole() == AppRole.CUSTOMER_ROLE) {
				filterOrder.setCustomer(customerService.get(BasicAuthenticationSession.get().getUserProfile().getId()));
			}
		}
		if (filterOrder.getFromDate() == null) {
			filterOrder.setFromDate(getFirstDateOfCurrentMonth());
		}
	}

	private Date getFirstDateOfCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		OrderDataProvider orderDataProvider = new OrderDataProvider();

		Form<FilterOrder> filterForm = getFilterOrderForm();
		add(filterForm);

		final WebMarkupContainer tableBody = new WebMarkupContainer("tableBody");

		tableBody.setOutputMarkupId(true);
		add(tableBody);

		DataView<Order> dataView = new DataView<Order>("tableRows", orderDataProvider, ITEMS_PER_PAGE) {
			@Override
			protected void populateItem(Item<Order> item) {
				final Order order = orderService.getWithDetails(item.getModelObject());
				final Auto auto = autoService.getWithDetails(order.getAuto());
				order.setAuto(auto);
				item.add(new Label("creationDate"));
				item.add(new Label("id"));

				final WebMarkupContainer itemOrderResult = new WebMarkupContainer("itemOrderResult");
				item.add(itemOrderResult);
				String orderResultClass;
				if (order.getOrderResult() == OrderResult.NONE) {
					orderResultClass = CSS_NONE;
				} else if (order.getOrderResult() == OrderResult.OK) {
					orderResultClass = CSS_OK;
				} else {
					orderResultClass = CSS_CANCELLED;
				}
				itemOrderResult.add(new AttributeModifier("class", new Model<String>(orderResultClass)));
				itemOrderResult.add(new AttributeModifier("title", new ResourceModel("OrderResult." + order.getOrderResult())));

				item.add(new Label("auto.car.regNum", new PropertyModel<String>(auto, "car.regNum")));

				// item.add(new Label("driverRating"));
				final WebMarkupContainer driverRatingContainter = new WebMarkupContainer("driverRating");
				final int driverRatingPerc = RatingClass.getRatingPercent(order.getDriverRating());
				driverRatingContainter.add(AttributeModifier.append("style", Model.of("width:" + driverRatingPerc + "%")));
				driverRatingContainter.add(AttributeModifier.append("title", Model.of(order.getDriverRating())));
				item.add(driverRatingContainter);

				// item.add(new Label("customerRating"));
				final WebMarkupContainer customerRatingContainter = new WebMarkupContainer("customerRating");
				final int customerRatingPerc = RatingClass.getRatingPercent(order.getCustomerRating());
				customerRatingContainter.add(AttributeModifier.append("style", Model.of("width:" + customerRatingPerc + "%")));
				customerRatingContainter.add(AttributeModifier.append("title", Model.of(order.getCustomerRating())));
				item.add(customerRatingContainter);

				item.add(new Label("orderPrice"));

				item.add(new Link<Void>("linkToEdit") {
					@Override
					public void onClick() {
						// setResponsePage(new OrderEditPage(order));
						setResponsePage(new CurrentOrderPage(new Model<Order>(order)) {
							@Override
							protected void onSetResponsePage() {
								// where go to back
								setResponsePage(new OrdersPage());
							}
						});
					}
				});

			}
		};

		tableBody.add(dataView);

		add(new PagingNavigator("paging", dataView));

	}

	private Form<FilterOrder> getFilterOrderForm() {
		Form<FilterOrder> filterForm = new Form<FilterOrder>("filterForm", new CompoundPropertyModel<FilterOrder>(
				filterOrder));

		final DateTextField filterFromDate = new DateTextField("fromDate",
				new PropertyModel<Date>(filterOrder, "fromDate"), new StyleDateConverter("M-", true));
		filterFromDate.add(AttributeModifier.append("title", new ResourceModel("p.filterOrder.fromDateTitle")));
		filterForm.add(filterFromDate);

		final DateTextField filterToDate = new DateTextField("toDate", new PropertyModel<Date>(filterOrder, "toDate"),
				new StyleDateConverter("M-", true));
		filterToDate.add(AttributeModifier.append("title", new ResourceModel("p.filterOrder.toDateTitle")));
		filterForm.add(filterToDate);

		DatePicker fromDatePicker = getDatePicker();
		DatePicker toDatePicker = getDatePicker();
		filterFromDate.add(fromDatePicker);
		filterToDate.add(toDatePicker);

		SubmitLink submitLink = new SubmitLink("linkToFilter") {
			@Override
			public void onSubmit() {
				setResponsePage(new OrdersPage(new Model<FilterOrder>(filterOrder)));
			}
		};
		filterForm.add(submitLink);
		return filterForm;
	}

	private DatePicker getDatePicker() {
		DatePicker datePicker = new DatePicker() {
			@Override
			protected String getAdditionalJavaScript() {
				return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
			}
		};
		datePicker.setShowOnFieldClick(true);
		datePicker.setAutoHide(true);
		return datePicker;
	}

	private class OrderDataProvider extends SortableDataProvider<Order, SingularAttribute<Order, ?>> {

		public OrderDataProvider() {
			super();
			setSort(Order_.creationDate, SortOrder.DESCENDING);
		}

		@Override
		public Iterator<? extends Order> iterator(long first, long count) {
			SingularAttribute<Order, ?> sortParam = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(sortParam);
			boolean ascending = SortOrder.ASCENDING.equals(propertySortOrder);
			return orderService.getAll(sortParam, ascending, (int) first, (int) count, filterOrder).iterator();
		}

		@Override
		public long size() {
			return orderService.getCount(filterOrder);
		}

		@Override
		public IModel<Order> model(Order order) {
			return new CompoundPropertyModel<Order>(order);
		}
	}

	public FilterOrder getFilterOrder() {
		if (filterOrder == null) {
			filterOrder = new FilterOrder();
		}
		if (filterOrder.getFromDate() == null) {
			filterOrder.setFromDate(getFirstDateOfCurrentMonth());
		}
		return filterOrder;
	}

	public void setFilterOrder(FilterOrder filterOrder) {
		this.filterOrder = filterOrder;
	}

}
