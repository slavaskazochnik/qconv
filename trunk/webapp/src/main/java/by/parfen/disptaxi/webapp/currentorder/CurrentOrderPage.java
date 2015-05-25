package by.parfen.disptaxi.webapp.currentorder;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.RangeValidator;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Route;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.datamodel.enums.OrderStatus;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.services.RouteService;
import by.parfen.disptaxi.services.UserProfileService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;
import by.parfen.disptaxi.webapp.etc.RatingClass;
import by.parfen.disptaxi.webapp.orders.OrdersPage;
import by.parfen.disptaxi.webapp.ordertimetable.OrderTimetablePanel;

public class CurrentOrderPage extends BaseLayout {

	@Inject
	private UserProfileService userProfileService;
	@Inject
	private AutoService autoService;
	@Inject
	private RouteService routeService;
	@Inject
	private OrderService orderService;
	@Inject
	private CustomerService customerService;
	@Inject
	private DriverService driverService;

	final Order order;
	Auto auto;
	UserProfile customerProfile;
	UserProfile driverProfile;
	List<Route> routes;

	final AppRole userAppRole;
	boolean canModifyOrder;
	boolean canModifyStatus;
	boolean userCanModifyResult;
	boolean canCancelOrder;
	boolean canRemoveOrder;
	boolean canConfirm;
	boolean showCustomerInfo;
	boolean showDriverInfo;
	boolean canModifyCustomer;
	boolean canModifyDriver;

	Long prevCustomerRating;
	Long prevDriverRating;

	public CurrentOrderPage(IModel<Order> orderModel) {
		super();
		order = orderModel.getObject();
		customerProfile = userProfileService.get(order.getCustomer().getId());
		auto = autoService.getWithDetails(order.getAuto());
		driverProfile = userProfileService.get(auto.getDriver().getId());
		routes = routeService.getAllByOrder(order);
		userAppRole = BasicAuthenticationSession.get().getUserAppRole();

		showCustomerInfo = userAppRole != AppRole.CUSTOMER_ROLE;
		showDriverInfo = userAppRole != AppRole.DRIVER_ROLE;
		canModifyCustomer = showCustomerInfo
				&& (order.getCustomerRating() == null || userAppRole == AppRole.ADMIN_ROLE || userAppRole == AppRole.OPERATOR_ROLE);
		canModifyDriver = showDriverInfo
				&& (order.getDriverRating() == null || userAppRole == AppRole.ADMIN_ROLE || userAppRole == AppRole.OPERATOR_ROLE);

		canRemoveOrder = userAppRole == AppRole.ADMIN_ROLE;
		canModifyOrder = (userAppRole == AppRole.ADMIN_ROLE || userAppRole == AppRole.OPERATOR_ROLE)
				&& order.getOrderStatus() != OrderStatus.DONE;
		canModifyOrder = true;
		canModifyStatus = (userAppRole == AppRole.ADMIN_ROLE || userAppRole == AppRole.OPERATOR_ROLE || userAppRole == AppRole.DRIVER_ROLE)
				&& order.getOrderStatus() != OrderStatus.DONE;

		canConfirm = canModifyStatus;
		canCancelOrder = order.getOrderStatus() != OrderStatus.DONE;

		prevCustomerRating = order.getCustomerRating();
		prevCustomerRating = prevCustomerRating == null ? 0 : prevCustomerRating;
		prevDriverRating = order.getDriverRating();
		prevDriverRating = prevDriverRating == null ? 0 : prevDriverRating;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<Order> form = new Form<Order>("inputForm", new CompoundPropertyModel<Order>(order));

		final TextField<String> tfUserId = new TextField<String>("id");
		tfUserId.setEnabled(false);
		form.add(tfUserId);

		// Customer info
		final TextField<String> customerName = new TextField<String>("customerFirstName", Model.of(customerProfile
				.getFirstName())) {
			@Override
			protected void onConfigure() {
				// visible only for not Customer users
				setVisibilityAllowed(showCustomerInfo);
			}
		};
		form.add(customerName);

		final TextField<String> customerTelNum = new TextField<String>("customerTelNum", Model.of(customerProfile
				.getTelNum()));
		form.add(customerTelNum);

		final TextField<String> tfCustomerRating = new TextField<String>("customerRating");
		tfCustomerRating.setLabel(new ResourceModel("p.user.avgRatingTitle"));
		tfCustomerRating.add(new RangeValidator<Long>(RatingClass.getMinRatingAsLong(), RatingClass.getMaxRatingAsLong()));
		tfCustomerRating.setEnabled(canModifyCustomer);
		form.add(tfCustomerRating);

		// Auto info (car and driver)
		final TextField<String> carRegNum = new TextField<String>("car.carInfo", Model.of(auto.getCar().getCarInfo()));
		form.add(carRegNum);

		final TextField<String> driverName = new TextField<String>("driverFirstName",
				Model.of(driverProfile.getFirstName())) {
			@Override
			protected void onConfigure() {
				// visible only for not Driver users
				setVisibilityAllowed(showDriverInfo);
			}
		};
		form.add(driverName);

		final TextField<String> driverTelNum = new TextField<String>("driverTelNum", Model.of(driverProfile.getTelNum()));
		form.add(driverTelNum);

		final TextField<String> tfDriverRating = new TextField<String>("driverRating");
		tfDriverRating.setLabel(new ResourceModel("p.user.avgRatingTitle"));
		tfDriverRating.add(new RangeValidator<Long>(RatingClass.getMinRatingAsLong(), RatingClass.getMaxRatingAsLong()));
		tfDriverRating.setEnabled(canModifyDriver);
		form.add(tfDriverRating);

		// Route info
		String srcAddress = null;
		if (routes.size() > 0) {
			srcAddress = routes.get(0).getSrcPointAddress();
		}
		final TextField<String> routeSrcAddress = new TextField<String>("routeSrcAddress", Model.of(srcAddress));
		form.add(routeSrcAddress);

		String dstAdress = null;
		if (routes.size() > 0) {
			dstAdress = routes.get(routes.size() - 1).getDstPointAddress();
		}
		final TextField<String> routeDstAddress = new TextField<String>("routeDstAddress", Model.of(dstAdress));
		form.add(routeDstAddress);

		final ResourceModel orderStatusModel = new ResourceModel("OrderStatus." + order.getOrderStatus());
		final TextField<String> orderStatus = new TextField<String>("orderStatus", orderStatusModel);
		form.add(orderStatus);

		final ResourceModel orderResultModel = new ResourceModel("OrderResult." + order.getOrderResult());
		final TextField<String> orderResult = new TextField<String>("orderResult", orderResultModel);
		form.add(orderResult);

		// OrderTimetable
		form.add(new OrderTimetablePanel("orderTimetableList", new Model<Order>(order)));

		// timetable buttons
		// Order buttons
		form.add(new SubmitLink("cancelOrderLink") {
			@Override
			protected void onConfigure() {
				setVisibilityAllowed(canCancelOrder);
			}

			@Override
			public void onSubmit() {
				super.onSubmit();
				OrderResult orderResult = OrderResult.CANCELED;
				if (userAppRole == AppRole.DRIVER_ROLE) {
					orderResult = OrderResult.CANCELED_BY_DRIVER;
				} else if (userAppRole == AppRole.CUSTOMER_ROLE) {
					orderResult = OrderResult.CANCELED_BY_CUSTOMER;
				}
				orderService.changeOrderResult(order, orderResult);
				onChangeOrderStatus();
			}
		});

		form.add(new SubmitLink("confirmOrderLink") {
			@Override
			protected void onConfigure() {
				setVisibilityAllowed(canConfirm);
			}

			@Override
			public void onSubmit() {
				super.onSubmit();
				orderService.changeOrderResult(order, OrderResult.OK);
				onChangeOrderStatus();
			}
		});

		final Link<Void> changeStatusLink = new Link<Void>("changeStatusLink") {
			@Override
			protected void onConfigure() {
				setVisibilityAllowed(canModifyStatus);
				if (canModifyStatus) {
					if (orderService.isLastOrderStatus(order.getOrderStatus())) {
						setVisibilityAllowed(false);
					} else {
						// final OrderStatus newOrderStatus =
						// orderService.getNextOrderStatus(order.getOrderStatus());
						// final ResourceModel newOrderStatusModel = new
						// ResourceModel("OrderStatus." + newOrderStatus);
						// this.add(new Label("newStatus", Model.of(newOrderStatusModel)));
					}
				}
			}

			@Override
			public void onClick() {
				orderService.changeOrderStatus(order, orderService.getNextOrderStatus(order.getOrderStatus()));
				onChangeOrderStatus();
			}
		};
		final OrderStatus newOrderStatus = orderService.getNextOrderStatus(order.getOrderStatus());
		final ResourceModel newOrderStatusModel = new ResourceModel("OrderStatus." + newOrderStatus);
		changeStatusLink.add(new Label("newStatus", Model.of(newOrderStatusModel)));
		form.add(changeStatusLink);

		// Order buttons
		form.add(new SubmitLink("sumbitLink") {
			@Override
			protected void onConfigure() {
				setVisibilityAllowed(canModifyOrder);
			}

			@Override
			public void onSubmit() {
				orderService.update(order);
				RecalculateRatings();
				onSetResponsePage();
			}
		});

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			protected void onConfigure() {
				setVisibilityAllowed(canRemoveOrder);
			}

			@Override
			public void onSubmit() {
				orderService.deleteWithDetails(order);
				RecalculateRatings();
				onSetResponsePage();
			}
		};
		form.add(removeLink);
		removeLink.setVisible(order.getId() != null);

		form.add(new SubmitLink("backLink") {
			@Override
			public void onSubmit() {
				onSetResponsePage();
			}
		}.setDefaultFormProcessing(false));

		add(form);
	}

	protected void RecalculateRatings() {
		if (order.getCustomerRating() != null && prevCustomerRating != order.getCustomerRating()) {
			final Customer customer = order.getCustomer();
			final BigDecimal customerAvgRating = orderService.getCustomerAvgRating(customer);
			customer.setAvgRating(customerAvgRating);
			customerService.update(customer);
		}
		if (order.getDriverRating() != null && prevDriverRating != order.getDriverRating()) {
			final Driver driver = order.getAuto().getDriver();
			final BigDecimal driverAvgRating = orderService.getDriverAvgRating(driver);
			driver.setAvgRating(driverAvgRating);
			driverService.update(driver);
		}
	}

	// Hook
	// where go to back
	protected void onSetResponsePage() {
		setResponsePage(new OrdersPage());
	}

	protected void onChangeOrderStatus() {
		// refresh Page
		setResponsePage(new CurrentOrderPage(new Model<Order>(order)));
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.currentOrder.pageTitle");
	}

}
