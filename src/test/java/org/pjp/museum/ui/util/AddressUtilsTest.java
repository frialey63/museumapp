package org.pjp.museum.ui.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AddressUtilsTest {

	@Test
	void testCheckAddressIsSecureDiscrete() {
		assertTrue(AddressUtils.checkAddressIsSecure("154.51.188.163", "154.51.188.163"));
		
		assertFalse(AddressUtils.checkAddressIsSecure("154.51.188.163", "154.51.188.162"));
		assertFalse(AddressUtils.checkAddressIsSecure("154.51.188.163", "154.51.188.164"));
	}

	@Test
	void testCheckAddressIsSecureDiscrete2() {
		assertTrue(AddressUtils.checkAddressIsSecure("154.51.188.163,146.198.19.248", "154.51.188.163"));
		assertTrue(AddressUtils.checkAddressIsSecure("154.51.188.163,146.198.19.248", "146.198.19.248"));
		
		assertFalse(AddressUtils.checkAddressIsSecure("154.51.188.163,146.198.19.248", "154.51.188.162"));
		assertFalse(AddressUtils.checkAddressIsSecure("154.51.188.163,146.198.19.248", "154.51.188.164"));
		assertFalse(AddressUtils.checkAddressIsSecure("154.51.188.163,146.198.19.248", "146.198.19.247"));
		assertFalse(AddressUtils.checkAddressIsSecure("154.51.188.163,146.198.19.248", "146.198.19.249"));
	}

	@Test
	void testCheckAddressIsSecureRange() {
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199", "172.172.172.1"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199", "172.172.172.66"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199", "172.172.172.199"));
		
		assertFalse(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199", "172.172.172.0"));
		assertFalse(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199", "172.172.172.200"));
	}

	@Test
	void testCheckAddressIsSecureRange2() {
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,192.168.0.1-192.168.0.254", "172.172.172.1"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,192.168.0.1-192.168.0.254", "172.172.172.66"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,192.168.0.1-192.168.0.254", "172.172.172.199"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,192.168.0.1-192.168.0.254", "192.168.0.1"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,192.168.0.1-192.168.0.254", "192.168.0.77"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,192.168.0.1-192.168.0.254", "192.168.0.254"));
		
		assertFalse(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,192.168.1-192.168.0.254", "172.172.172.0"));
		assertFalse(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,192.168.1-192.168.0.254", "172.172.172.200"));
		assertFalse(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,192.168.1-192.168.0.254", "192.168.0.0"));
		assertFalse(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,192.168.1-192.168.0.254", "192.168.0.255"));
	}

	@Test
	void testCheckAddressIsSecure() {
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,154.51.188.163", "154.51.188.163"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,154.51.188.163", "172.172.172.1"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,154.51.188.163", "172.172.172.66"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,154.51.188.163", "172.172.172.199"));
		
		assertFalse(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,154.51.188.163", "154.51.188.162"));
		assertFalse(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,154.51.188.163", "154.51.188.164"));
		assertFalse(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,154.51.188.163", "172.172.172.0"));
		assertFalse(AddressUtils.checkAddressIsSecure("172.172.172.1-172.172.172.199,154.51.188.163", "172.172.172.200"));
	}

	@Test
	void testCheckAddressIsSecureFormatted() {
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1 - 172.172.172.199, 154.51.188.163 ", "154.51.188.163"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1 - 172.172.172.199, 154.51.188.163 ", "172.172.172.1"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1 - 172.172.172.199, 154.51.188.163 ", "172.172.172.66"));
		assertTrue(AddressUtils.checkAddressIsSecure("172.172.172.1 - 172.172.172.199, 154.51.188.163 ", "172.172.172.199"));
		
		assertFalse(AddressUtils.checkAddressIsSecure(" 172.172.172.1 - 172.172.172.199, 154.51.188.163", "154.51.188.162"));
		assertFalse(AddressUtils.checkAddressIsSecure(" 172.172.172.1 - 172.172.172.199, 154.51.188.163", "154.51.188.164"));
		assertFalse(AddressUtils.checkAddressIsSecure(" 172.172.172.1 - 172.172.172.199, 154.51.188.163", "172.172.172.0"));
		assertFalse(AddressUtils.checkAddressIsSecure(" 172.172.172.1 - 172.172.172.199, 154.51.188.163", "172.172.172.200"));
	}

}
