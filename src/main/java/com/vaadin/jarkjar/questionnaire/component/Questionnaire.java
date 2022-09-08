package com.vaadin.jarkjar.questionnaire.component;

import com.vaadin.jarkjar.questionnaire.model.QuestionSet;
import com.vaadin.jarkjar.questionnaire.model.UserAnswerSet;

/**
 * @author Paul Parlett
 *
 */
public interface Questionnaire {

    void setQuestionSet(QuestionSet questionSet);

    boolean isValid();

    UserAnswerSet getUserAnswerSet();
}