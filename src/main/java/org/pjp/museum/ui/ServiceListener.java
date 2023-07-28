package org.pjp.museum.ui;

import org.apache.logging.log4j.util.Strings;
import org.pjp.museum.service.SessionRecordService;
import org.pjp.museum.ui.util.AddressUtils;
import org.pjp.museum.ui.util.SettingsUtil;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.accessdenied.AccessDeniedView;
import org.pjp.museum.ui.view.admin.AdminView;
import org.pjp.museum.ui.view.stats.StatsView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;

@Component
public class ServiceListener implements VaadinServiceInitListener {

    private static final long serialVersionUID = -3678291874101081863L;

    private static final Logger LOGGER = LoggerFactory.getLogger(VaadinServiceInitListener.class);
    
    private static boolean SECURE = false;
    
    @Value("${enable.admin:false}")
    private boolean enableAdmin;

    @Value("${enable.stats:true}")
    private boolean enableStats;

    @Value("${secure.addresses}")
    private String secureAddresses;

	@SuppressWarnings("unchecked")
    @Override
    public void serviceInit(ServiceInitEvent event) {
        LOGGER.info("enableAdmin = {}" + enableAdmin);

        RouteConfiguration configuration = RouteConfiguration.forApplicationScope();

        if (enableAdmin) {
            configuration.setRoute("admin", AdminView.class, MainLayout.class);
        }

        if (enableStats) {
            configuration.setRoute("stats", StatsView.class, MainLayout.class);
        }

        event.getSource().addSessionInitListener(initEvent -> {
            VaadinSession vaadinSession = initEvent.getSession();
            
            LOGGER.debug("A new Session {} has been initialised", vaadinSession.getSession().getId());
            SettingsUtil.setMode(vaadinSession, SettingsUtil.QR_CODE);
            
            SessionRecordService service = StaticHelper.getBean(SessionRecordService.class);
            service.createRecord(vaadinSession);
        });
        
        event.getSource().addSessionDestroyListener(destroyEvent -> {
            VaadinSession vaadinSession = destroyEvent.getSession();
            WrappedSession wrappedSession = vaadinSession.getSession();
            
			LOGGER.debug("Session {} has been destroyed, saving a SessionRecord", wrappedSession.getId());
			
            SessionRecordService service = StaticHelper.getBean(SessionRecordService.class);
            service.finaliseRecord(vaadinSession);
        });
        
        if (SECURE) {
            LOGGER.info("secureAddresses = {}", secureAddresses);

            event.getSource().addUIInitListener(uiEvent -> {
                UI ui = uiEvent.getUI();

                if (Strings.isNotEmpty(secureAddresses)) {
                    ui.addBeforeEnterListener(l -> {
                        // check whether App is being accessed on Museum wifi network, if not redirect to the Access Denied page

                    	String ipAddress = AddressUtils.getRealAddress(ui.getSession());
                        LOGGER.debug("IP address = {}", ipAddress);
                        
                    	boolean result = AddressUtils.checkAddressIsSecure(secureAddresses, ipAddress);
                    	
                    	if (!result) {
                            LOGGER.debug("IP address {} is not within the secure addresses {}", ipAddress, secureAddresses);
                            l.rerouteTo(AccessDeniedView.class);
                    	}
                    });
                }
            });
        }
    }

}