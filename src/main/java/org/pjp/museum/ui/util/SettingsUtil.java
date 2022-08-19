package org.pjp.museum.ui.util;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

public final class SettingsUtil {

    public static final String QR_CODE = "QR Code";
    public static final String NUMBER = "Number";

    private static final String EXHIBIT_IDENTIFICATION = "EXHIBIT_IDENTIFICATION";

    public static String getMode() {
        return (String) UI.getCurrent().getSession().getAttribute(EXHIBIT_IDENTIFICATION);
    }

    public static boolean isScanning() {
        Object value = UI.getCurrent().getSession().getAttribute(EXHIBIT_IDENTIFICATION);
        return QR_CODE.equals(value);
    }

    public static void setMode(VaadinSession session, String mode) {
        session.setAttribute(EXHIBIT_IDENTIFICATION, mode);
    }

    private SettingsUtil() {
        // prevent instantiation
    }
}
