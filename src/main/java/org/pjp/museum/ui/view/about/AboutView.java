package org.pjp.museum.ui.view.about;

import org.pjp.museum.ui.view.MainLayout;
import org.springframework.beans.factory.annotation.Value;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.AnchorTarget;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
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

    @Value("${about.facebook.url}")
    private String aboutFacebookUrl;

    @Value("${about.instagram.url}")
    private String aboutInstagramUrl;

    @Value("${application.version}")
    private String applicationVersion;

    @Value("${privacy-policy.url}")
    private String privacyPolicyUrl;

    private final Image museumImage = new Image("images/empty-plant.png", "logo");

    private final Anchor museumAnchor = new Anchor("http://google.co.uk/", museumImage);

    private final Image facebookImage = new Image("icons/facebook.png", "facebook");

    private final Image instagramImage = new Image("icons/instagram.png", "instagram");

    private final Anchor facebookAnchor = new Anchor("http://google.co.uk/", facebookImage);

    private final Anchor instagramAnchor = new Anchor("http://google.co.uk/", instagramImage);

    private final H2 heading = new H2();

    private final Paragraph appParagraph = new Paragraph();

    private final Anchor privacyPolicyAnchor = new Anchor("https://en.wikipedia.org/wiki/Privacy_policy", "Privacy Policy", AnchorTarget.BLANK);

    public AboutView() {
        super();

        setSpacing(false);

        add(museumAnchor, heading, new Span(facebookAnchor, new Span(" "), instagramAnchor), appParagraph, privacyPolicyAnchor);

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

        facebookAnchor.setHref(aboutFacebookUrl);
        instagramAnchor.setHref(aboutInstagramUrl);

        heading.setText(aboutHeading);

        appParagraph.setText(APP_NAME + applicationVersion);

        privacyPolicyAnchor.setHref(privacyPolicyUrl);
    }

}
