package org.pjp.museum.ui;

import java.net.UnknownHostException;

import org.apache.logging.log4j.util.Strings;
import org.pjp.museum.ui.util.SettingsUtil;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.accessdenied.AccessDeniedView;
import org.pjp.museum.ui.view.importcsv.ImportCsvView;
import org.pjp.museum.util.IPWithGivenRangeCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;

@Component
public class ServiceListener implements VaadinServiceInitListener {

    private static final long serialVersionUID = -3678291874101081863L;

    private static final Logger LOGGER = LoggerFactory.getLogger(VaadinServiceInitListener.class);

    @Value("${enable.csv.import:false}")
    private boolean enableCsvImport;

    @Value("${secure.address.range}")
    private String secureAddressRange;

    @SuppressWarnings("unchecked")
    @Override
    public void serviceInit(ServiceInitEvent event) {
        LOGGER.info("enableCsvImport = {}" + enableCsvImport);

        if (enableCsvImport) {
            RouteConfiguration configuration = RouteConfiguration.forApplicationScope();

            configuration.setRoute("importcsv", ImportCsvView.class, MainLayout.class);
        }

        event.getSource().addSessionInitListener(initEvent -> {
            VaadinSession vaadinSession = initEvent.getSession();
            LOGGER.debug("A new Session {} has been initialized!", vaadinSession.getSession().getId());
            SettingsUtil.setMode(vaadinSession, SettingsUtil.QR_CODE);
        });

        LOGGER.info("secureAddressRange = {}", secureAddressRange);

        event.getSource().addUIInitListener(uiEvent -> {
            UI ui = uiEvent.getUI();

            if (Strings.isNotEmpty(secureAddressRange)) {
                ui.addBeforeEnterListener(l -> {
                    String[] secureAddressRangeArr = secureAddressRange.split("-");

                    if (secureAddressRangeArr.length == 2) {
                        LOGGER.debug("secureAddressRangeArr[0] = {}, secureAddressRangeArr[1] = {}", secureAddressRangeArr[0], secureAddressRangeArr[1]);

                        String ipAddress = ui.getSession().getBrowser().getAddress();
                        LOGGER.info("IP address = {}", ipAddress);

                        // check whether App is being accessed on Museum wifi network, if not redirect to the Access Denied page
                        try {
                            if (!IPWithGivenRangeCheck.checkIPv4IsInRangeByConvertingToInt(ipAddress, secureAddressRangeArr[0], secureAddressRangeArr[1])) {
                                LOGGER.debug("IP address {} is not within the secure address range {}", ipAddress, secureAddressRange);
                                l.rerouteTo(AccessDeniedView.class);
                            }
                        } catch (UnknownHostException e) {
                            LOGGER.error("failed to check IP address is in secure address range", e);
                        }
                    } else {
                        LOGGER.debug("error in specification of the secure address range {}", secureAddressRange);
                    }
                });
            }
        });
    }

}