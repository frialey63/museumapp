package com.vaadin.jarkjar.questionnaire.component;

import java.util.List;

import com.vaadin.jarkjar.questionnaire.model.QuestionSet;
import com.vaadin.jarkjar.questionnaire.model.UserAnswer;

/**
 * @author Paul Parlett
 *
 */
public interface Questionnaire {

    void setQuestionSet(QuestionSet questionSet);

    QuestionSet getQuestionSet();

    boolean isValid();

    List<UserAnswer> getUserAnswers();
}