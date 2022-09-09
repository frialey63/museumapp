package org.pjp.museum.ui.view.survey;

import org.pjp.museum.service.QuestionnaireService;
import org.pjp.museum.service.QuestionnaireService.Category;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.util.UuidStr;
import org.vaadin.addons.pjp.questionnaire.component.QuestionnaireComponent;
import org.vaadin.addons.pjp.questionnaire.model.QuestionSet;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Emphasis;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Visitor Survey")
@Route(value = "survey", layout = MainLayout.class)
public class VisitorSurveyView extends VerticalLayout implements AfterNavigationObserver {

    private static final long serialVersionUID = 1957778223224073460L;

    private final Label allDoneLabel = new Label("All Done, Thank You");
    {
        allDoneLabel.getStyle().set("font-size", "x-large");
        allDoneLabel.getStyle().set("color", "green");
    }

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
            service.saveUserAnswerSet(museumQuestionnaire.getUserAnswerSet(UuidStr.random()));
            museumQuestionnaire.setEnabled(false);
        });

        visitorQuestionnaire.addSubmitButtonClickListener(l -> {
            service.saveUserAnswerSet(visitorQuestionnaire.getUserAnswerSet(UuidStr.random()));
            visitorQuestionnaire.setEnabled(false);
        });

        add(intro, anon, allDoneLabel, museumQuestionnaire, visitorQuestionnaire);

        setHorizontalComponentAlignment(Alignment.CENTER, allDoneLabel);

        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        String sessionId = UI.getCurrent().getSession().getSession().getId();

        QuestionSet museumQuestionSet = service.getQuestionSet(Category.MUSEUM);
        QuestionSet visitorQuestionSet = service.getQuestionSet(Category.VISITOR);

        boolean allDone = true;

        if (!service.hasUserAnswerSet(museumQuestionSet.getUuid(), sessionId)) {
            museumQuestionnaire.setQuestionSet(museumQuestionSet);
            allDone = false;
        }

        if (!service.hasUserAnswerSet(visitorQuestionSet.getUuid(), sessionId)) {
            visitorQuestionnaire.setQuestionSet(visitorQuestionSet);
            allDone = false;
        }

        allDoneLabel.setVisible(allDone);
    }

}
