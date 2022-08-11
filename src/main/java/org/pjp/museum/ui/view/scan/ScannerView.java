package org.pjp.museum.ui.view.scan;

import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.ui.component.html5qrcode.Html5Qrcode;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.exhibit.ExhibitView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Scan QR Code")
@Route(value = "scanner", layout = MainLayout.class)
public class ScannerView extends VerticalLayout {

    private static final long serialVersionUID = -4302145178663286253L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerView.class);

    private static final boolean TEST_MODE = true;

    // TODO reversionary mode if no access to the local camera...

    public ScannerView() {
        super();

        if (TEST_MODE) {
            configureTestMode();
        } else {
            Html5Qrcode component = new Html5Qrcode(sc -> {
                LOGGER.debug("uuid = {}", sc.getUuid());

                UI.getCurrent().navigate(ExhibitView.class, sc.getUuid());
            });
            add(component);
        }
    }

    private void configureTestMode() {
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
