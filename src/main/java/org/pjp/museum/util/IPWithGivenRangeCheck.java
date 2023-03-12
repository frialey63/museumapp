package org.pjp.museum.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/** 
 * {@link https://www.baeldung.com/java-check-ip-address-range} 
 */
public final class IPWithGivenRangeCheck {

    private static long ipToLongInt (InetAddress ipAddress) {
        long resultIP = 0;
        byte[] ipAddressOctets = ipAddress.getAddress();

        for (byte octet : ipAddressOctets) {
            resultIP <<= 8;
            resultIP |= octet & 0xFF;
        }
        
        return resultIP;
    }

    public static boolean checkIPv4IsInRangeByConvertingToInt(String inputIP, String rangeStartIP, String rangeEndIP) throws UnknownHostException {
        long startIPAddress = ipToLongInt(InetAddress.getByName(rangeStartIP));
        long endIPAddress = ipToLongInt(InetAddress.getByName(rangeEndIP));
        long inputIPAddress = ipToLongInt(InetAddress.getByName(inputIP));

        return (inputIPAddress >= startIPAddress && inputIPAddress <= endIPAddress);
    }

    private IPWithGivenRangeCheck() {
        // prevent instantiation
    }
}
