package org.pjp.museum.ui.view.intro;

import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.exhibit.ExhibitView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
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

    private final String descriptionStr = """
                Many of the exhibits in this museum have a QR Code. Scan the code using this app to access an audio description of the exhibit. If the scanner does not work then the exhibit can be identified by using a manually selected (tail) number.
                <br/><br/>
                <em>For the best experience we recommend using headphones otherwise we respectfully request that the volume on your phone handset is minimised to avoid disturbance to other visitors.</em>
            """;

    public IntroductionView() {
        super();

        setSpacing(false);

        H2 title = new H2("Welcome to the Museum App");

        Paragraph description = new Paragraph();
        description.getElement().setProperty("innerHTML", descriptionStr);


        Button startButton = new Button("Start Tour", e -> {
            String uuid = ExhibitService.MUSEUM_UUID;

            UI.getCurrent().navigate(ExhibitView.class, uuid);
        });

        setHorizontalComponentAlignment(Alignment.CENTER, title, startButton);
        setHorizontalComponentAlignment(Alignment.START, description);

        add(title, description, startButton);

        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        WebBrowser browser = VaadinSession.getCurrent().getBrowser();
        LOGGER.info(browser.getBrowserApplication());
    }

}
