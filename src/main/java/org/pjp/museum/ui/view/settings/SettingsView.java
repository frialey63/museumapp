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

        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Exhibit Identification Mode");
        radioGroup.setItems(SettingsUtil.QR_CODE, SettingsUtil.NUMBER);
        radioGroup.setValue(SettingsUtil.getMode());


        setHorizontalComponentAlignment(Alignment.START, radioGroup);

        add(radioGroup);

        radioGroup.addValueChangeListener(l -> {
            SettingsUtil.setMode(UI.getCurrent().getSession(), l.getValue());
        });

        setSizeFull();
    }

}
