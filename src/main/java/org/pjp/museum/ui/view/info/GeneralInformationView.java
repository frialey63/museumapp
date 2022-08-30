package org.pjp.museum.ui.view.info;

import org.pjp.museum.service.MuseumService;
import org.pjp.museum.ui.view.MainLayout;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("General Information")
@Route(value = "info", layout = MainLayout.class)
public class GeneralInformationView extends VerticalLayout implements AfterNavigationObserver {

    private static final long serialVersionUID = 4163118643385525651L;

    private final Paragraph museumParagraph = new Paragraph();

    private final MuseumService museumService;

    public GeneralInformationView(MuseumService museumService) {
        super();
        this.museumService = museumService;

        setSpacing(false);

        {
            H4 heading = new H4("Closing Time");

            add(heading, museumParagraph);
        }

        {
            H4 heading = new H4("Toilets");

            Paragraph paragraph = new Paragraph("Toilets are located behind the T-133 in the middle hangar. From the entrance follow the corridor out of the shop, turn right at the first hangar and then right again at the second hangar.");

            add(heading, paragraph);
        }

        {
            H4 heading = new H4("Wheel Chair Access");

            Paragraph paragraph = new Paragraph("The museum galleries on the first floor may be accessed via a ramp. From the entrance follow the corridor out of the shop, go straight across the hangar through the Armoury and bear left through The Blitz sound and light display.");

            add(heading, paragraph);
        }

        {
            H4 heading = new H4("The Museum Shop");

            Paragraph paragraph = new Paragraph("The museum shop at the entrance has a wide selection of souvenirs, books, models and toys. Don't forget to reserve some time at the end of your visit to peruse.");

            add(heading, paragraph);
        }

        {
            H4 heading = new H4("The NAAFI Café");

            Paragraph paragraph = new Paragraph("The NAAFI Café serves homemade family favourite light bites, such as breakfast butties and delicious range of homemade cakes and afternoon teas.");

            add(heading, paragraph);
        }

        {
            H4 heading = new H4("The NAAFI Emporium");

            Paragraph paragraph = new Paragraph("Located at the back of the cafe, the NAAFI Emporium is host to an assortment of local arts and crafts available to purchase.");

            add(heading, paragraph);
        }

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);

        getStyle().set("text-align", "start");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        museumParagraph.setText(String.format("The museum closes at %s, this app will notify you when closing time approaches.", museumService.getClosingTime()));
    }

}
