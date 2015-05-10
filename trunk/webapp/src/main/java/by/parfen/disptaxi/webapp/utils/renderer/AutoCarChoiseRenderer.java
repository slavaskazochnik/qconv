package by.parfen.disptaxi.webapp.utils.renderer;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

import by.parfen.disptaxi.datamodel.Car;

public class AutoCarChoiseRenderer implements IChoiceRenderer<Car> {

	public static AutoCarChoiseRenderer INSTANCE = new AutoCarChoiseRenderer();

	private AutoCarChoiseRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Car car) {
		return car.getRegNum();
	}

	@Override
	public String getIdValue(Car car, int index) {
		return car.getId().toString();
	}

}
