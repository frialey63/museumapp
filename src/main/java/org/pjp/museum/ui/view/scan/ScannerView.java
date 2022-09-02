package org.pjp.museum.ui.view.scan;

import java.util.HashMap;
import java.util.Map;

import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.exhibit.ExhibitView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.pjp.html5qrcode.Html5Qrcode;
import org.vaadin.addons.pjp.html5qrcode.Html5Qrcode.Option;

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

        Map<String, Object> options = new HashMap<>();

        options.put(Option.fps.name(), 10);
        options.put(Option.qrbox.name(), 250);
        options.put(Option.supportedScanTypes.name(), "[Html5QrcodeScanType.SCAN_TYPE_CAMERA]");

        Html5Qrcode component = new Html5Qrcode(sc -> {
            LOGGER.debug("uuid = {}", sc.getValue());

            UI.getCurrent().navigate(ExhibitView.class, sc.getValue());
        }, options);

        component.setWidthFull();
        component.setHeightFull();

        setHorizontalComponentAlignment(Alignment.CENTER, component);

        add(component);

        setSizeFull();
    }
}
