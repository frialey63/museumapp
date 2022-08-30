package org.pjp.museum.ui.view.settings;

import org.pjp.museum.ui.util.SettingsUtil;
import org.pjp.museum.ui.view.MainLayout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
public class SettingsView extends VerticalLayout {

    private static final long serialVersionUID = 2627524281352852715L;

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

        setHorizontalComponentAlignment(Alignment.START, idMode, deafAccess, language);
        add(idMode, deafAccess, language);

        idMode.addValueChangeListener(l -> {
            SettingsUtil.setMode(UI.getCurrent().getSession(), l.getValue());
        });

        setSizeFull();
    }

}
