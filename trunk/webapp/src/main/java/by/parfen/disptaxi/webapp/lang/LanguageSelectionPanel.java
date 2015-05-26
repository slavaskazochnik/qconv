package by.parfen.disptaxi.webapp.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class LanguageSelectionPanel extends Panel {

	private static final List<Locale> LOCALES = new ArrayList<>();

	static {
		LOCALES.add(new Locale("en"));
		LOCALES.add(new Locale("ru"));
	}

	public LanguageSelectionPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Session session = getSession();
		add(new DropDownChoice<Locale>("lang", new Model<Locale>(session.getLocale()), LOCALES, new ChoiceRenderer<>(
				"displayLanguage", "displayLanguage")) {
			@Override
			protected boolean wantOnSelectionChangedNotifications() {
				return true;
			};

			@Override
			protected void onSelectionChanged(Locale newSelection) {
				getSession().setLocale(newSelection);

			};

		}.setNullValid(false));
	}

}
