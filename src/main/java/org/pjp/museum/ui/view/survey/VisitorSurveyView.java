package org.pjp.museum.ui.view.survey;

import org.pjp.museum.service.QuestionnaireService;
import org.pjp.museum.ui.view.MainLayout;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Visitor Survey")
@Route(value = "survey", layout = MainLayout.class)
public class VisitorSurveyView extends VerticalLayout implements AfterNavigationObserver {

    private static final long serialVersionUID = 1957778223224073460L;

    private final H2 heading = new H2("Coming Soon");

    private final QuestionnaireService service;

    public VisitorSurveyView(QuestionnaireService service) {
        super();
        this.service = service;

        setSpacing(false);

        add(heading);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        getStyle().set("text-align", "center");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
    }

}
