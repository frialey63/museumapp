package org.pjp.museum.ui.view.kids;

import org.pjp.museum.ui.view.MainLayout;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
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
            add(new H2("All Year"));

            add(new H4("Manston Railway"));
            add(new Image("images/manston_railway_400.jpg", "Manston Railway"));
        }

        {
            add(new H2("School Holiday Extras"));

            add(new H4("Teddy Hunt"));
            add(new Image("images/teddy_hunt_400.jpg", "Teddy Hunt"));

            add(new H4("Drawing Table"));
            add(new Image("images/drawing_table_400.jpg", "Drawing Table"));
        }

        setWidthFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        getStyle().set("text-align", "start");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
    }

}
