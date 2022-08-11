package org.pjp.museum.ui;

import org.pjp.museum.ui.util.SettingsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//import com.vaadin.componentfactory.IdleNotification;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

@Component
public class ServiceListener implements VaadinServiceInitListener {

    private static final long serialVersionUID = -3678291874101081863L;

    private static final Logger LOGGER = LoggerFactory.getLogger(VaadinServiceInitListener.class);

    @Override
    public void serviceInit(ServiceInitEvent event) {

        event.getSource().addSessionInitListener(initEvent -> {
            LOGGER.debug("A new Session has been initialized!");
            SettingsUtil.setMode(SettingsUtil.QR_CODE);
        });

    }
}