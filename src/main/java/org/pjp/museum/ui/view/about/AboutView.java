package org.pjp.museum.ui.view.about;

import org.pjp.museum.ui.view.MainLayout;
import org.springframework.beans.factory.annotation.Value;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends VerticalLayout implements AfterNavigationObserver {

    private static final long serialVersionUID = -4389648159491795815L;

    private static final String APP_NAME = "Museum App version ";

    @Value("${about.heading}")
    private String aboutHeading;

    @Value("${about.museum.image}")
    private String aboutMuseumImage;

    @Value("${about.museum.url}")
    private String aboutMuseumUrl;

    @Value("${about.cafe.image}")
    private String aboutCafeImage;

    @Value("${about.cafe.url}")
    private String aboutCafeUrl;

    @Value("${application.version}")
    private String applicationVersion;

    private final Image museumImage = new Image("images/empty-plant.png", "logo");

    private final Anchor museumAnchor = new Anchor("http://google.co.uk/", museumImage);

    private final Image cafeImage = new Image("images/empty-plant.png", "logo");

    private final Anchor cafeAnchor = new Anchor("http://google.co.uk/", cafeImage);

    private final H2 heading = new H2();

    private final Paragraph appParagraph = new Paragraph();

    public AboutView() {
        super();

        setSpacing(false);

        VerticalLayout vl1 = new VerticalLayout(museumAnchor);
        vl1.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        VerticalLayout vl2 = new VerticalLayout(cafeAnchor);
        vl2.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(vl1, vl2, heading, appParagraph);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        getStyle().set("text-align", "center");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        museumImage.setSrc(aboutMuseumImage);
        museumImage.setWidth("200px");
        museumAnchor.setHref(aboutMuseumUrl);

        cafeImage.setSrc(aboutCafeImage);
        cafeImage.setWidth("200px");
        cafeAnchor.setHref(aboutCafeUrl);

        heading.setText(aboutHeading);

        appParagraph.setText(APP_NAME + applicationVersion);
    }

}
