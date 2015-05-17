package by.parfen.disptaxi.webapp.etc;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class ItemPanel extends Panel {

	private String itemInfo;

	public String getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(String itemInfo) {
		this.itemInfo = itemInfo;
	}

	public ItemPanel(String id) {
		super(id);
		itemInfo = "put item info here";
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final WebMarkupContainer itemDetails = new WebMarkupContainer("itemDetails");
		add(itemDetails);

		final TextField<String> tfItemInfo = new TextField<String>("itemInfo", Model.of(itemInfo));
		itemDetails.add(tfItemInfo);
		tfItemInfo.add(new OnChangeAjaxBehavior() {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				final String valueAsString = getComponent().getDefaultModelObjectAsString();
				itemInfo = valueAsString;
			}
		});

	}

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
	}

}
