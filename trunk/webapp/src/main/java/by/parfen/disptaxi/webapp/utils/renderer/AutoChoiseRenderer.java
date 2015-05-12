package by.parfen.disptaxi.webapp.utils.renderer;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

import by.parfen.disptaxi.datamodel.Auto;

public class AutoChoiseRenderer implements IChoiceRenderer<Auto> {

	public static AutoChoiseRenderer INSTANCE = new AutoChoiseRenderer();

	private AutoChoiseRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Auto auto) {
		String result;
		result = auto.getCar().getRegNum();
		if (auto.getCar().getCarModel() != null) {
			result += " " + auto.getCar().getCarModel();
		}
		// result += ". " + auto.getDriver().getUserProfile().getUserInfo();
		return result;
	}

	@Override
	public String getIdValue(Auto auto, int index) {
		return auto.getId().toString();
	}

}