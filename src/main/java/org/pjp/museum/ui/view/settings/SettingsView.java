package org.pjp.museum.ui.view.settings;

import org.pjp.museum.ui.util.SettingsUtil;
import org.pjp.museum.ui.view.MainLayout;
import org.springframework.beans.factory.annotation.Value;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
public class SettingsView extends VerticalLayout implements AfterNavigationObserver {

    private static final long serialVersionUID = 2627524281352852715L;

    @Value("${chrome-flags.visible:false}")
    private boolean chromeFlagsVisible;
    
    private final TextField chromeFlags = new TextField("Chrome Flags");

    public SettingsView() {
        super();

        setSpacing(false);

        RadioButtonGroup<String> idMode = new RadioButtonGroup<>();
        idMode.setLabel("Exhibit Identification Mode");
        idMode.setItems(SettingsUtil.QR_CODE, SettingsUtil.NUMBER);
        idMode.setValue(SettingsUtil.getMode());

        RadioButtonGroup<String> deafAccess = new RadioButtonGroup<>();
        deafAccess.setEnabled(false);
        deafAccess.setLabel("For the Hard of Hearing");
        deafAccess.setItems("Audio", "Text");
        deafAccess.setValue("Audio");

        RadioButtonGroup<String> language = new RadioButtonGroup<>();
        language.setEnabled(false);
        language.setLabel("Language");
        language.setItems("English", "French", "German");
        language.setValue("English");

        chromeFlags.setValue("chrome://flags/#unsafely-treat-insecure-origin-as-secure");
        chromeFlags.setReadOnly(true);
        chromeFlags.setWidthFull();
        
        setHorizontalComponentAlignment(Alignment.START, idMode, deafAccess, language, chromeFlags);
        add(idMode, deafAccess, language, chromeFlags);
        
        idMode.addValueChangeListener(l -> {
            String mode = l.getValue();
			SettingsUtil.setMode(UI.getCurrent().getSession(), mode);
        });

        setSizeFull();
    }

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		chromeFlags.setVisible(chromeFlagsVisible);
	}

}
