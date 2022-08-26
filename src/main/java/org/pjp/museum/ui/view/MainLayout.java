package org.pjp.museum.ui.view;


import org.pjp.museum.ui.collab.Broadcaster;
import org.pjp.museum.ui.component.appnav.AppNav;
import org.pjp.museum.ui.component.appnav.AppNavItem;
import org.pjp.museum.ui.view.about.AboutView;
import org.pjp.museum.ui.view.intro.IntroductionView;
import org.pjp.museum.ui.view.number.TailNumberView;
import org.pjp.museum.ui.view.scan.ScannerView;
import org.pjp.museum.ui.view.settings.SettingsView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.shared.Registration;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private static final long serialVersionUID = 6777899833487418585L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MainLayout.class);

    private H1 viewTitle;

    private Registration broadcasterRegistration;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();

        broadcasterRegistration = Broadcaster.register(newMessage -> {
            ui.access(() -> {
                LOGGER.debug("received message {}", newMessage);

                switch (newMessage.messageType()) {
                case CLOSING_TIME:
                    String msg = String.format("Museum will be closing in %s minutes", newMessage.minutes());
                    Notification.show(msg, 30, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_PRIMARY);
                    break;
                case CLOSED:
                    Notification.show("The museum is closed, please make your way to the shop and exit.", 0, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_ERROR);
                    break;
                }
            });
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassNames("view-toggle");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames("view-title");

        Header header = new Header(toggle, viewTitle);
        header.addClassNames("view-header");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Museum App");
        appName.addClassNames("app-name");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
                createNavigation(), createFooter());
        section.addClassNames("drawer-section");
        return section;
    }

    private AppNav createNavigation() {
        AppNav nav = new AppNav();
        nav.addClassNames("app-nav");

        nav.addItem(new AppNavItem("Introduction", IntroductionView.class, "la la-home"));
        nav.addItem(new AppNavItem("Scan QR Code", ScannerView.class, "la la-qrcode"));
        nav.addItem(new AppNavItem("Enter Tail Number", TailNumberView.class, "la la-keyboard-o"));
        nav.addItem(new AppNavItem("Settings", SettingsView.class, "la la-cog"));
        nav.addItem(new AppNavItem("About", AboutView.class, "la la-info"));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("app-nav-footer");

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
