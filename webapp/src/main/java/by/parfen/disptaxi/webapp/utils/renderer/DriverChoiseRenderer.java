package by.parfen.disptaxi.webapp.utils.renderer;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

import by.parfen.disptaxi.datamodel.Driver;

public class DriverChoiseRenderer implements IChoiceRenderer<Driver> {

	public static DriverChoiseRenderer INSTANCE = new DriverChoiseRenderer();

	private DriverChoiseRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Driver driver) {
		return driver.getUserProfile().getUserInfo();
	}

	@Override
	public String getIdValue(Driver driver, int index) {
		return driver.getId().toString();
	}

}