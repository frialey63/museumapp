package org.pjp.museum.ui.view.scan;

import org.pjp.museum.ui.component.html5qrcode.Html5Qrcode;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.exhibit.ExhibitView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Scan QR Code")
@Route(value = "scanner", layout = MainLayout.class)
public class ScannerView extends VerticalLayout {

    private static final long serialVersionUID = -4302145178663286253L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerView.class);

    public ScannerView() {
        super();

        Html5Qrcode component = new Html5Qrcode(sc -> {
            LOGGER.debug("uuid = {}", sc.getUuid());

            UI.getCurrent().navigate(ExhibitView.class, sc.getUuid());
        });

        component.setWidthFull();
        component.setHeightFull();

        setHorizontalComponentAlignment(Alignment.CENTER, component);

        add(component);

        setSizeFull();
    }
}
