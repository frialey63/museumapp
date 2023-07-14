package org.pjp.museum.ui.view.scan;

import org.pjp.museum.service.SessionRecordService;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.exhibit.ExhibitView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.wontlost.zxing.Constants;
import com.wontlost.zxing.ZXingVaadinReader;

@PageTitle("Scan QR Code")
@JsModule("vaadin-zxing-reader.js")
@NpmPackage(value = "@zxing/browser", version = "^0.1.1")
@Route(value = "scanner", layout = MainLayout.class)
public class ScannerZxingView extends VerticalLayout {

    private static final long serialVersionUID = 5665801440827713574L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerZxingView.class);

    public ScannerZxingView(SessionRecordService service) {
        super();

        ZXingVaadinReader zxingReader = new ZXingVaadinReader();
        zxingReader.setFrom(Constants.From.camera);
        zxingReader.setId("video"); //id needs to be 'video' if From.camera.

        zxingReader.addValueChangeListener(e -> {
            String tailNumber = e.getValue();
			LOGGER.debug("tailNumber = {}", tailNumber);
            
            service.updateRecord(VaadinSession.getCurrent(), tailNumber, true);

            UI.getCurrent().navigate(ExhibitView.class, tailNumber);
        });

        zxingReader.setWidthFull();
        zxingReader.setHeightFull();

        setHorizontalComponentAlignment(Alignment.CENTER, zxingReader);

        add(zxingReader);

        setSizeFull();
    }
}
