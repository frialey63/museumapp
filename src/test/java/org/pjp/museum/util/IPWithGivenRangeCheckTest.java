package org.pjp.museum.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IPWithGivenRangeCheckTest {

    @Test
    void givenIPv4Addresses_whenIsInRange_thenReturnsTrue() throws Exception {
        assertTrue(IPWithGivenRangeCheck.checkIPv4IsInRangeByConvertingToInt("192.220.3.0", "192.210.0.0", "192.255.0.0"));
    }

    @Test
    void givenIPv4Addresses_whenIsNotInRange_thenReturnsFalse() throws Exception {
        assertFalse(IPWithGivenRangeCheck.checkIPv4IsInRangeByConvertingToInt("192.200.0.0", "192.210.0.0", "192.255.0.0"));
    }

}
