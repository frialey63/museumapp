package org.pjp.museum.ui.util;

import org.apache.logging.log4j.util.Strings;

import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

public final class AddressUtils {

	/**
	 * Get the real IP address where there may be a redirect by a proxy or load-balancer.  
	 * @param session
	 * @return
	 */
	public static String getRealAddress(VaadinSession session) {
        String header = VaadinService.getCurrentRequest().getHeader("X-Forwarded-For");
        
        if (Strings.isNotBlank(header)) {
        	return header.split(",")[0].trim();
        }
        
		return session.getBrowser().getAddress();
	}

    private AddressUtils() {
        // prevent instantiation
    }

}
