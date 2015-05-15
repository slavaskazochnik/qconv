package by.parfen.disptaxi.webapp.neworder;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Route;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.webapp.BaseLayout;
import by.parfen.disptaxi.webapp.autos.panel.AutoInlinePanel;
import by.parfen.disptaxi.webapp.orders.OrdersPage;

public class Page2Autos extends BaseLayout {

	@Inject
	private AutoService autoService;

	private static Route route;
	private static Auto auto;

	public Page2Autos(final Route route) {
		Injector.get().inject(this);
		this.route = route;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		// final List<Auto> allAutos =
		// autoService.getAllActiveByCarTypeAndGeo(CarType.SEDAN,
		// route.getSrcPoint()
		// .getPositionLat(), route.getSrcPoint().getPositionLng());
		final List<Auto> allAutos = autoService.getAllWithDetails();
		add(new ListView<Auto>("detailsPanel", allAutos) {
			@Override
			protected void populateItem(ListItem<Auto> item) {
				final Auto auto = item.getModelObject();
				item.add(new AutoInlinePanel("itemPanel", auto));
			}
		});

		final AutoForm autosForm = new AutoForm("inputForm", new Auto());
		add(autosForm);
	}

	static public final class AutoForm extends Form<Auto> {

		public AutoForm(String id, final Auto auto) {
			super(id, new CompoundPropertyModel<Auto>(auto));

			add(new SubmitLink("cancelLink") {
				@Override
				public void onSubmit() {
					setResponsePage(new OrdersPage());
				}
			}.setDefaultFormProcessing(false));

			add(new SubmitLink("backLink") {
				@Override
				public void onSubmit() {
					setResponsePage(new Page1Route(route));
				}
			}.setDefaultFormProcessing(false));

			add(new SubmitLink("sumbitLink") {
				@Override
				public void onSubmit() {
					super.onSubmit();
					final Page3Confirm page = new Page3Confirm(route, auto);
					setResponsePage(page);
				}
			});

		}
	}
}