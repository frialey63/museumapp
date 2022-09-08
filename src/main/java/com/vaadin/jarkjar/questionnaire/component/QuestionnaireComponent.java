package com.vaadin.jarkjar.questionnaire.component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.pjp.museum.ui.component.CompactVerticalLayout;
import org.pjp.museum.util.UuidStr;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.shared.Registration;
import com.vaadin.jarkjar.questionnaire.model.Question;
import com.vaadin.jarkjar.questionnaire.model.QuestionSet;
import com.vaadin.jarkjar.questionnaire.model.UserAnswer;
import com.vaadin.jarkjar.questionnaire.model.UserAnswerSet;

/**
 * Represents complete Questionnaire in QuestionnaireWidget.
 *
 * @author Paul Parlett
 * @author Jarkko Järvinen
 *
 */
public class QuestionnaireComponent extends CompactVerticalLayout implements ComponentEventListener<ClickEvent<Button>>, Questionnaire {

    private static final long serialVersionUID = -6523431408378169759L;

    public static final String LUMO_REQUIRED_INDICATOR = "•";

    private QuestionSet questionSet;

    private List<UserAnswer> userAnswers = new ArrayList<UserAnswer>();

    private boolean valid;

    /**
     * Questionnaire title goes here
     */
    private final H4 questionnaireTitle;

    /**
     * Questionnaire description goes here
     */
    private final Span questionnaireDescription;

    /**
     * Contains question controls for QuestionnaireWidget those will be shown in form
     */
    private final List<QuestionComponent> questionControls = new ArrayList<QuestionComponent>();

    /**
     * Submit button for QuestionnaireWidget
     */
    private final Button submitButton;

    /**
     * Default constructor
     */
    public QuestionnaireComponent() {
        questionnaireTitle = new H4();

        questionnaireDescription = new Span();

        submitButton = new Button("Submit");
        submitButton.addClickListener(this);
    }

    public Registration addSubmitButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        return submitButton.addClickListener(listener);
    }

    public void removeSubmitButtonClickListener(Registration listenerRegistration) {
        listenerRegistration.remove();
    }

    @Override
    public void setQuestionSet(QuestionSet questionSet) {
        this.questionSet = questionSet;

        List<Question> questions = questionSet.getQuestions();

        QuestionSet.validateOptionAnswers(questionSet);

        // Clear exist questionControls
        questionControls.clear();

        removeAll();

        String title = questionSet.getText();
        if (title != null) {
            questionnaireTitle.setText(title);
            add(questionnaireTitle);
        }

        String description = questionSet.getDescription();
        if (description != null) {
            questionnaireDescription.setText(description);
            add(questionnaireDescription);
        }

        // Add new ones to widget
        for (Question question : questions) {
            QuestionComponent qc = new QuestionComponent(question);
            questionControls.add(qc);

            add(qc);
        }

        // Set custom text to submitButton
        String text = questionSet.getSubmitButtonText();
        if (text != null) {
            submitButton.setText(text);
        }

        // We need this submit button to indicate server that user has been answered
        add(submitButton);
    }

    @Override
    public UserAnswerSet getUserAnswerSet() {
        String sessionId = UI.getCurrent().getSession().getSession().getId();
        return new UserAnswerSet(UuidStr.random(), questionSet.getUuid(), userAnswers, Instant.now(), sessionId);
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void onComponentEvent(ClickEvent<Button> event) {
        valid = true;

        userAnswers.clear();

        for (QuestionComponent qc : questionControls) {
            qc.getUserAnswer().ifPresentOrElse(userAnswer -> userAnswers.add(userAnswer), () -> { valid = false; });
        }
    }
}
