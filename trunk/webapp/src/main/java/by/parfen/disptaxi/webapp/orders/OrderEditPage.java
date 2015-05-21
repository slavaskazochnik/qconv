package by.parfen.disptaxi.webapp.orders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.datamodel.enums.OrderStatus;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.services.PriceService;
import by.parfen.disptaxi.services.UserProfileService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.utils.renderer.AutoChoiseRenderer;
import by.parfen.disptaxi.webapp.utils.renderer.CustomerChoiseRenderer;

public class OrderEditPage extends BaseLayout {

	public OrderEditPage(final Order order) {
		super();

		if (order.getId() == null) {
			order.setOrderStatus(OrderStatus.NEW);
			order.setOrderResult(OrderResult.NONE);
		}
		final OrderForm orderForm = new OrderForm("inputForm", order);
		add(orderForm);
	}

	static public final class OrderForm extends Form<Order> {

		@Inject
		private OrderService orderService;
		@Inject
		private CustomerService customerService;
		@Inject
		private AutoService autoService;
		@Inject
		private PriceService priceService;

		private Auto selectedAuto;
		private Customer selectedCustomer;

		public OrderForm(String id, final Order order) {
			super(id, new CompoundPropertyModel<Order>(order));

			final TextField<String> tfUserId = new TextField<String>("id");
			tfUserId.setEnabled(false);
			add(tfUserId);

			List<Customer> customersList = new ArrayList<Customer>();
			customersList.addAll(customerService.getAllWithDetails());
			if (order.getId() != null) {
				selectedCustomer = order.getCustomer();
			}
			final DropDownChoice<Customer> ddcCustomer = new DropDownChoice<Customer>("customer",
					new PropertyModel<Customer>(this, "selectedCustomer"), customersList, CustomerChoiseRenderer.INSTANCE);
			add(ddcCustomer);

			List<Auto> autosList = new ArrayList<Auto>();
			autosList.addAll(autoService.getAllWithDetails());
			if (order.getId() != null) {
				selectedAuto = order.getAuto();
			}
			final DropDownChoice<Auto> ddcAuto = new DropDownChoice<Auto>("auto", new PropertyModel<Auto>(this,
					"selectedAuto"), autosList, AutoChoiseRenderer.INSTANCE);
			add(ddcAuto);

			Auto auto = null;
			if (order.getId() != null) {
				auto = autoService.getWithDetails(order.getAuto());
			}
			final AutoForm autoForm = new AutoForm("autoForm", auto);
			add(autoForm);

			final TextField<String> tfRouteLength = new TextField<String>("routeLength");
			tfRouteLength.setLabel(new ResourceModel("p.car.edit.routeLengthTitle"));
			tfRouteLength.add(new PropertyValidator<String>());
			add(tfRouteLength);

			final TextField<String> tfOrderPrice = new TextField<String>("orderPrice");
			tfOrderPrice.setLabel(new ResourceModel("p.car.edit.orderPriceTitle"));
			tfOrderPrice.add(new PropertyValidator<String>());
			add(tfOrderPrice);

			final TextField<String> tfCustomerRating = new TextField<String>("customerRating");
			tfCustomerRating.setLabel(new ResourceModel("p.car.edit.customerRatingTitle"));
			tfCustomerRating.add(new PropertyValidator<String>());
			add(tfCustomerRating);

			final TextField<String> tfDriverRating = new TextField<String>("driverRating");
			tfDriverRating.setLabel(new ResourceModel("p.car.edit.driverRatingTitle"));
			tfDriverRating.add(new PropertyValidator<String>());
			add(tfDriverRating);

			DropDownChoice<OrderStatus> ddcOrderStatus = new DropDownChoice<OrderStatus>("orderStatus",
					Arrays.asList(OrderStatus.values()), new EnumChoiceRenderer<OrderStatus>(this));
			ddcOrderStatus.setLabel(new ResourceModel("p.car.edit.orderStatusTitle"));
			ddcOrderStatus.add(new PropertyValidator<OrderStatus>());
			add(ddcOrderStatus);
			ddcOrderStatus.setEnabled(order.getOrderResult() == OrderResult.NONE);

			DropDownChoice<OrderResult> ddcOrderResult = new DropDownChoice<OrderResult>("orderResult",
					Arrays.asList(OrderResult.values()), new EnumChoiceRenderer<OrderResult>(this));
			ddcOrderResult.setLabel(new ResourceModel("p.car.edit.orderResultTitle"));
			ddcOrderResult.add(new PropertyValidator<OrderResult>());
			add(ddcOrderResult);
			ddcOrderResult.setEnabled(order.getOrderStatus() == OrderStatus.DONE);

			add(new SubmitLink("cancelLink") {
				@Override
				public void onSubmit() {
					setResponsePage(new OrdersPage());
				}
			}.setDefaultFormProcessing(false));

			add(new SubmitLink("sumbitLink") {
				@Override
				public void onSubmit() {
					super.onSubmit();
					if (selectedAuto != null) {
						final Price price = priceService.getByCarType(selectedAuto.getCar().getCarType());
						order.setPrice(price);
					}
					order.setCustomer(selectedCustomer);
					order.setAuto(selectedAuto);
					if (order.getId() == null) {
						order.setCreationDate(new Date());
						orderService.create(order);
					} else {
						orderService.update(order);
					}
					orderService.changeOrderStatus(order, order.getOrderStatus());
					orderService.changeOrderResult(order, order.getOrderResult());
					OrdersPage page = new OrdersPage();
					setResponsePage(page);
				}
			});

			final SubmitLink removeLink = new SubmitLink("removeLink") {
				@Override
				public void onSubmit() {
					super.onSubmit();
					orderService.deleteWithDetails(order);
					setResponsePage(new OrdersPage());
				}
			};
			add(removeLink);
			removeLink.setDefaultFormProcessing(false);
			removeLink.setVisible(order.getId() != null);
		}

		static public final class AutoForm extends Form<Auto> {

			@Inject
			private UserProfileService userProfileService;

			public AutoForm(String id, Auto auto) {
				super(id, new CompoundPropertyModel<Auto>(auto));

				final TextField<String> tfCarModel = new TextField<String>("car.carModel");
				add(tfCarModel);
				tfCarModel.setEnabled(false);

				Driver driver = null;
				if (auto != null) {
					driver = auto.getDriver();
					driver.setUserProfile(userProfileService.get(driver.getId()));
				}
				Form<Driver> driverForm = new DriverForm("driverForm", driver);
				add(driverForm);
			}

		}

		static public final class DriverForm extends Form<Driver> {

			public DriverForm(String id, Driver driver) {
				super(id, new CompoundPropertyModel<Driver>(driver));

				final TextField<String> tfDriver = new TextField<String>("userProfile.getUserInfo");
				add(tfDriver);
				tfDriver.setEnabled(false);
			}

		}

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("p.order.edit.pageTitle");
	}
}
