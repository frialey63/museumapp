package org.pjp.museum.ui.view.kids;

import org.pjp.museum.ui.view.MainLayout;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Kids Stuff")
@Route(value = "kids", layout = MainLayout.class)
public class KidsStuffView extends VerticalLayout implements AfterNavigationObserver {

    private static final long serialVersionUID = 3949730046039724630L;

    public KidsStuffView() {
        super();

        setSpacing(false);

        {
            add(new H4("The Youngest Ones"));

            add(new H6("Drawing Table"));
            add(new Paragraph("todo"));

            add(new H6("Teddy Hunt"));
            add(new Paragraph("todo"));

            add(new H6("Disney Flight Simulator"));
            add(new Paragraph("todo"));
        }

        {
            add(new H4("For Older Ones"));

            add(new H6("Code Breakers"));
            add(new Paragraph("todo"));

            add(new H6("Manston Railway"));
            add(new Paragraph("todo"));
        }

        {
            add(new H4("We're All Kids"));

            add(new H6("Open Cockpits"));
            add(new Paragraph("todo"));

            add(new H6("Tank Rides"));
            add(new Paragraph("todo"));
        }

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);

        getStyle().set("text-align", "start");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
    }

}
