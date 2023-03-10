package org.pjp.museum.ui.view.intro;

import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.exhibit.ExhibitView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
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
                Many of the exhibits in this museum have a QR Code. Scan the code using this App to access an audio description of the exhibit. If the scanner does not work then the exhibit can be identified by using a manually selected (tail) number.
                <br/><br/>
                For the best App experience we recommend the following set-up on your mobile:
                <ul>
                    <li>Install to the Android home screen when prompted (or via Install option on the Chrome menu)</li>
                    <li>Use headphones, otherwise please minimise speaker volume to avoid disturbance to other visitors</li>
                </ul>
                <em>Note the screensaver may cause the scanner to fail. In this case it will be necessary to restart the app.</em>
            """;

    public IntroductionView() {
        super();

        setSpacing(false);

        Image image = new Image("images/raf-manston-logo_66pct.png", "logo");

        H2 title = new H2("Welcome to the Museum App");

        Paragraph description = new Paragraph();
        description.getElement().setProperty("innerHTML", descriptionStr);


        Button startButton = new Button("Start Tour", e -> {
            String uuid = ExhibitService.MUSEUM_UUID;

            UI.getCurrent().navigate(ExhibitView.class, uuid);
        });

        setHorizontalComponentAlignment(Alignment.CENTER, title, startButton);
        setHorizontalComponentAlignment(Alignment.START, description);

        add(image, title, description, startButton);

        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        WebBrowser browser = VaadinSession.getCurrent().getBrowser();
        LOGGER.info(browser.getBrowserApplication());
    }

}
