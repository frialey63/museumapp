package org.pjp.museum.ui.view.scan;

import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.exhibit.ExhibitView;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Scan QR Code")
@Route(value = "scanner", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ScannerView extends VerticalLayout {

    private static final long serialVersionUID = -4302145178663286253L;

    public ScannerView() {
        Button button = new Button("SCAN (Buccaneer)", e -> {
            String uuid = ExhibitService.TEST_BUCCANEER_UUID;

            UI.getCurrent().navigate(ExhibitView.class, uuid);
        });
        add(button);

        button = new Button("SCAN (Canberra)", e -> {
            String uuid = ExhibitService.TEST_CANBERRA_UUID;

            UI.getCurrent().navigate(ExhibitView.class, uuid);
        });
        add(button);

        button = new Button("SCAN (Hunter)", e -> {
            String uuid = ExhibitService.TEST_HUNTER_UUID;

            UI.getCurrent().navigate(ExhibitView.class, uuid);
        });
        add(button);
    }
}
