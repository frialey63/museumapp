package org.pjp.museum.ui.view.survey;

import org.pjp.museum.service.QuestionnaireService;
import org.pjp.museum.service.QuestionnaireService.Category;
import org.pjp.museum.ui.view.MainLayout;

import com.vaadin.flow.component.html.Emphasis;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.jarkjar.questionnaire.component.QuestionnaireComponent;

@PageTitle("Visitor Survey")
@Route(value = "survey", layout = MainLayout.class)
public class VisitorSurveyView extends VerticalLayout implements AfterNavigationObserver {

    private static final long serialVersionUID = 1957778223224073460L;

    private final QuestionnaireComponent museumQuestionnaire = new QuestionnaireComponent();

    private final QuestionnaireComponent visitorQuestionnaire = new QuestionnaireComponent();

    private final QuestionnaireService service;

    public VisitorSurveyView(QuestionnaireService service) {
        super();
        this.service = service;

        setSpacing(false);

        Paragraph intro = new Paragraph("Please take a few minutes to complete our survey. We aim to utilise the results from the survey to improve the museum.");

        Emphasis anon = new Emphasis("All results are anonymous.");

        museumQuestionnaire.addSubmitButtonClickListener(l -> {
            // TODO save questionnaire from a single(?) submit button and disable this questionnaire for the session
        });

        visitorQuestionnaire.addSubmitButtonClickListener(l -> {
            // TODO save questionnaire from a single(?) submit button and disable this questionnaire for the session
        });

        add(intro, anon, museumQuestionnaire, visitorQuestionnaire);

        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        museumQuestionnaire.setQuestionSet(service.getQuestionSet(Category.MUSEUM));
        visitorQuestionnaire.setQuestionSet(service.getQuestionSet(Category.VISITOR));
    }

}
