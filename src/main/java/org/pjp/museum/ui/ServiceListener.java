package org.pjp.museum.ui;

import org.pjp.museum.ui.util.SettingsUtil;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.importcsv.ImportCsvView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

@Component
public class ServiceListener implements VaadinServiceInitListener {

    private static final long serialVersionUID = -3678291874101081863L;

    private static final Logger LOGGER = LoggerFactory.getLogger(VaadinServiceInitListener.class);

    @Value("${enable.csv.import:false}")
    private boolean enableCsvImport;

    @SuppressWarnings("unchecked")
    @Override
    public void serviceInit(ServiceInitEvent event) {
        LOGGER.debug("enableCsvImport = {}" + enableCsvImport);

        if (enableCsvImport) {
            RouteConfiguration configuration = RouteConfiguration.forApplicationScope();

            configuration.setRoute("importcsv", ImportCsvView.class, MainLayout.class);
        }

        event.getSource().addSessionInitListener(initEvent -> {
            LOGGER.debug("A new Session has been initialized!");
            SettingsUtil.setMode(initEvent.getSession(), SettingsUtil.QR_CODE);
        });

    }
}