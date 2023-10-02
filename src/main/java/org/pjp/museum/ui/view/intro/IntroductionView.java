package org.pjp.museum.ui.view.intro;

import org.apache.logging.log4j.util.Strings;
import org.pjp.museum.model.Exhibit;
import org.pjp.museum.ui.util.AddressUtils;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.exhibit.ExhibitView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

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
            Many of the exhibits in this museum have a QR Code. Scan the code using app to access an audio description. If scanner fails then an exhibit can be identified using a number via "Enter Tail Number" page on menu.
            <br/><br/>
            %s
            For the best App experience we recommend the following set-up on your mobile:
            <ul>
                <li>On Android, "Add App to Home screen" when prompted <em>and restart app from icon</em></li>
                <li>Use headphones or minimise speaker volume to avoid disturbance to other visitors</li>
                <li>Audio is mono therefore wireless ear pieces may be shared between two people</li>
            </ul>
        """;

    private final String connectStr = """
            <mark>Connect to "Manston History Public Access" wi-fi</mark>
             <br/><br/>
        """;

    @Value("${secure.addresses}")
    private String secureAddresses;

    private final Paragraph description = new Paragraph();
    
    public IntroductionView() {
        super();

        setSpacing(false);

        Image image = new Image("images/Museum_Logo_h150a.jpg", "logo");
        image.setWidth("50%");

        H2 title = new H2("Welcome to the Museum App");

        Button startButton = new Button("Start Tour", e -> {
            UI.getCurrent().navigate(ExhibitView.class, Exhibit.MUSEUM);
        });

        setHorizontalComponentAlignment(Alignment.CENTER, title, startButton);
        setHorizontalComponentAlignment(Alignment.START, description);

        add(image, title, description, startButton);

        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        description.getElement().setProperty("innerHTML", String.format(descriptionStr, (isAllowed(UI.getCurrent()) ? "" : connectStr)));
        
        WebBrowser browser = VaadinSession.getCurrent().getBrowser();
        LOGGER.debug(browser.getBrowserApplication());
    }
    
    private boolean isAllowed(UI ui) {
    	if (Strings.isNotEmpty(secureAddresses)) {
        	String ipAddress = AddressUtils.getRealAddress(ui.getSession());
            LOGGER.debug("IP address = {}", ipAddress);
           
        	return AddressUtils.checkAddressIsSecure(secureAddresses, ipAddress);
    	}
    	
    	return true;
    }

}
