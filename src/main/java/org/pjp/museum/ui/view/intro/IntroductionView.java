package org.pjp.museum.ui.view.intro;

import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.scan.ScannerView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;

@PageTitle("Introduction")
@Route(value = "intro", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class IntroductionView extends VerticalLayout implements AfterNavigationObserver {

    private static final long serialVersionUID = 4289783921529905080L;

    private static final Logger LOGGER = LoggerFactory.getLogger(IntroductionView.class);

    public IntroductionView() {
        super();

        setSpacing(false);

        Button start = new Button("Start Tour", e -> {
            UI.getCurrent().navigate(ScannerView.class);
        });

        add(new H2("TODO"), start);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        getStyle().set("text-align", "center");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        WebBrowser browser = VaadinSession.getCurrent().getBrowser();
        LOGGER.info(browser.getBrowserApplication());
    }

}
