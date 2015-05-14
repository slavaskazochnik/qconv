package by.parfen.disptaxi.webapp.orders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.datamodel.enums.OrderStatus;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.services.PriceService;
import by.parfen.disptaxi.services.UserProfileService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.utils.renderer.AutoChoiseRenderer;
import by.parfen.disptaxi.webapp.utils.renderer.CustomerChoiseRenderer;

public class OrderEditPageCopy extends BaseLayout {

	@Inject
	private OrderService orderService;
	@Inject
	private CustomerService customerService;
	@Inject
	private DriverService driverService;
	@Inject
	private AutoService autoService;
	@Inject
	private PriceService priceService;
	@Inject
	private UserProfileService userProfileService;

	private Auto selectedAuto;
	private Customer selectedCustomer;
	private Driver selectedDriver;

	public OrderEditPageCopy(final Order order) {
		super();
		// order.setAuto(autoService.getWithDetails(order.getAuto()));

		Form<Order> form = new Form<Order>("inputForm", new CompoundPropertyModel<Order>(order));

		final TextField<String> tfUserId = new TextField<String>("id");
		tfUserId.setEnabled(false);
		form.add(tfUserId);

		List<Customer> customersList = new ArrayList<Customer>();
		customersList.addAll(customerService.getAllWithDetails());
		if (order.getId() != null) {
			selectedCustomer = order.getCustomer();
		}
		final DropDownChoice<Customer> ddcCustomer = new DropDownChoice<Customer>("customer", new PropertyModel<Customer>(
				this, "selectedCustomer"), customersList, CustomerChoiseRenderer.INSTANCE);
		form.add(ddcCustomer);

		List<Auto> autosList = new ArrayList<Auto>();
		autosList.addAll(autoService.getAllWithDetails());
		if (order.getId() != null) {
			selectedAuto = order.getAuto();
		}
		final DropDownChoice<Auto> ddcAuto = new DropDownChoice<Auto>("auto",
				new PropertyModel<Auto>(this, "selectedAuto"), autosList, AutoChoiseRenderer.INSTANCE);
		form.add(ddcAuto);

		form.add(new SubmitLink("cancelLink") {
			@Override
			public void onSubmit() {
				setResponsePage(new OrdersPage());
			}
		}.setDefaultFormProcessing(false));

		/*
		 * AUTO DETAILS
		 */
		final Auto auto = autoService.getWithDetails(order.getAuto());
		Form<Auto> autoForm = new Form<Auto>("autoForm", new CompoundPropertyModel<Auto>(auto));
		final TextField<String> tfCarModel = new TextField<String>("car.carModel");
		autoForm.add(tfCarModel);
		tfCarModel.setEnabled(false);

		final Driver driver = driverService.get(auto.getDriver().getId());
		driver.setUserProfile(userProfileService.get(driver.getId()));
		Form<Driver> driverForm = new Form<Driver>("driverForm", new CompoundPropertyModel<Driver>(driver));
		final TextField<String> tfDriver = new TextField<String>("userProfile.getUserInfo");
		driverForm.add(tfDriver);
		tfDriver.setEnabled(false);

		autoForm.add(driverForm);

		form.add(autoForm);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				final Price price = priceService.getByCarType(selectedAuto.getCar().getCarType());
				order.setPrice(price);
				order.setCustomer(selectedCustomer);
				order.setAuto(selectedAuto);
				if (order.getId() == null) {
					order.setOrderStatus(OrderStatus.NEW);
					order.setOrderResult(OrderResult.NONE);
					order.setCreationDate(new Date());
					orderService.create(order);
				} else {
					orderService.update(order);
				}
				OrdersPage page = new OrdersPage();
				setResponsePage(page);
			}
		});

		final SubmitLink removeLink = new SubmitLink("removeLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				orderService.delete(order);
				setResponsePage(new OrdersPage());
			}
		};
		form.add(removeLink);
		removeLink.setDefaultFormProcessing(false);
		removeLink.setVisible(order.getId() != null);

		add(form);
	}
}
