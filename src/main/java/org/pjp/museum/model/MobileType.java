package org.pjp.museum.model;

import com.vaadin.flow.server.WebBrowser;

public enum MobileType {

	ANDROID, IPHONE, WINDOWS_PHONE, OTHER;
	
	public static MobileType get(WebBrowser browser) {
		if (browser.isAndroid()) {
			return ANDROID;
		} else if (browser.isIPhone()) {
			return IPHONE;
		} else if (browser.isWindowsPhone()) {
			return WINDOWS_PHONE;
		}
	
		return OTHER;
	}
}
