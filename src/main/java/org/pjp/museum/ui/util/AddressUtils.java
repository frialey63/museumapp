package org.pjp.museum.ui.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import org.apache.logging.log4j.util.Strings;
import org.pjp.museum.util.IPWithGivenRangeCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

public final class AddressUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressUtils.class);

    /**
     * Get the real IP address where there may be a redirect by a proxy or load-balancer.
     * @param session
     * @return
     */
    public static String getRealAddress(VaadinSession session) {
        VaadinRequest currentRequest = VaadinService.getCurrentRequest();

        String header = currentRequest.getHeader("X-Forwarded-For");
        if (Strings.isNotBlank(header)) {
            return header.split(",")[0].trim();
        }

        String remoteAddr = currentRequest.getRemoteAddr();
        if (Strings.isNotBlank(remoteAddr)) {
            return remoteAddr;
        }

        return session.getBrowser().getAddress();
    }

    /**
     * @param secureAddresses The secure addresses
     * @param ipAddress The IP address to check
     * @return True if IP address is in the secure addresses
     */
    public static boolean checkAddressIsSecure(String secureAddresses, String ipAddress) {
        boolean result = false;

        String[] addressRangeArr = secureAddresses.split(",");

        for (String addressRange : addressRangeArr) {
            String[] addressArr = addressRange.split("-");

            String lower;
            String upper;

            if (addressArr.length == 2) {
                lower = addressArr[0].trim();
                upper = addressArr[1].trim();
            } else {
                lower = addressArr[0].trim();
                upper = addressArr[0].trim();
            }

            LOGGER.debug("lower = {}, upper = {}", lower, upper);

            try {
                String host = new URI("http://" + ipAddress).getHost();

                if (IPWithGivenRangeCheck.checkIPv4IsInRangeByConvertingToInt(host, lower, upper)) {
                    result = true;
                    break;
                }
            } catch (UnknownHostException | URISyntaxException e) {
                LOGGER.error("failed to check IP address is in secure addresses", e);
            }
        }

        return result;
    }

    private AddressUtils() {
        // prevent instantiation
    }

}
