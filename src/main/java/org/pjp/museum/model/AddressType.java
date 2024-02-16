package org.pjp.museum.model;

import org.pjp.museum.ui.util.AddressUtils;

public enum AddressType {

    SECURE, OTHER;

    public static AddressType getAddressType(String secureAddresses, String ipAddress) {
        if (AddressUtils.checkAddressIsSecure(secureAddresses, ipAddress)) {
            return SECURE;
        }

        return OTHER;
    }
}
