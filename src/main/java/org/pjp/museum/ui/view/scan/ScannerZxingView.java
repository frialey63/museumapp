package org.pjp.museum.ui.view.scan;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.pjp.museum.service.SessionRecordService;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.exhibit.ExhibitView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

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

    private static Map<String, String> getQueryMap(String query) {
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<>();
		
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			map.put(name, value);
		}
		
		return map;
	}

    @Value("${app.download.url}")
    private String appDownloadUrl;

    public ScannerZxingView(SessionRecordService service) {
        super();

        ZXingVaadinReader zxingReader = new ZXingVaadinReader();
        zxingReader.setFrom(Constants.From.camera);
        zxingReader.setId("video"); //id needs to be 'video' if From.camera.

        zxingReader.addValueChangeListener(l -> {
        	try {
				URL url = new URL(l.getValue());
				Map<String, String> map = getQueryMap(url.getQuery());
				
	            String tailNumber = map.get(org.pjp.museum.util.Constants.TAIL_NUMBER);
				LOGGER.debug("tailNumber = {}", tailNumber);
				
	            UI ui = UI.getCurrent();

                service.updateRecord(VaadinSession.getCurrent(), tailNumber, true);
                ui.navigate(ExhibitView.class, tailNumber);

        	} catch (MalformedURLException e) {
				LOGGER.error("failed to create the URL from the scanned value", e);
			}
        });

        zxingReader.setWidthFull();
        zxingReader.setHeightFull();

        setHorizontalComponentAlignment(Alignment.CENTER, zxingReader);

        add(zxingReader);

        setSizeFull();
    }
}
