package org.pjp.museum.model;

import com.vaadin.flow.server.WebBrowser;

public enum MobileType {

	ANDROID("Android"), IPHONE("iPhone"), WINDOWS_PHONE("Windows Phone"), OTHER("Other");
	
	private final String string;
	
	MobileType(String string) {
		this.string = string;
	}
	
	@Override
	public String toString() {
		return string;
	}

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
