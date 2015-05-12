package by.parfen.disptaxi.webapp.orders;

import java.util.ArrayList;
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
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.utils.renderer.AutoChoiseRenderer;
import by.parfen.disptaxi.webapp.utils.renderer.CustomerChoiseRenderer;

public class OrderEditPage extends BaseLayout {

	@Inject
	private OrderService orderService;
	@Inject
	private CustomerService customerService;
	@Inject
	private DriverService driverService;
	@Inject
	private AutoService autoService;

	private Customer selectedCustomer;
	private Driver selectedDriver;
	private Auto selectedAuto;

	public OrderEditPage(final Order order) {
		super();
		Form<Order> form = new Form<Order>("inputForm", new CompoundPropertyModel<Order>(order));

		final TextField<String> tfUserId = new TextField<String>("id");
		tfUserId.setEnabled(false);
		form.add(tfUserId);

		// final TextField<String> tfRegNum = new TextField<String>("regNum");
		// tfRegNum.setLabel(new ResourceModel("p.order.edit.regNumTitle"));
		// tfRegNum.add(new PropertyValidator<String>());
		// form.add(tfRegNum);

		// List<Driver> driversList = new ArrayList<Driver>();
		// driversList.addAll(driverService.getAllWithDetails());
		// if (order.getId() != null) {
		// selectedDriver = orderService.getOrderDriver(order);
		// }
		// final DropDownChoice<Driver> ddcDriver = new
		// DropDownChoice<Driver>("driver", new PropertyModel<Driver>(this,
		// "selectedDriver"), driversList,
		// DriverChoiseRenderer.INSTANCE);
		// form.add(ddcDriver);

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

		final Auto auto = autoService.getWithDetails(order.getAuto());
		Form<Auto> autosForm = new Form<Auto>("autosForm", new CompoundPropertyModel<Auto>(auto));
		final TextField<String> tfCarModel = new TextField<String>("car.carModel");
		autosForm.add(tfCarModel);
		tfCarModel.setEnabled(false);

		form.add(autosForm);

		form.add(new SubmitLink("sumbitLink") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				order.setAuto(selectedAuto);
				orderService.update(order);
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
