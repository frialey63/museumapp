package com.vaadin.jarkjar.questionnaire.model;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.jarkjar.questionnaire.model.Question.QuestionType;

/**
 * Represents set of questions comprising questionnaire.
 *
 * @author Paul Parlett
 * @author Jarkko Järvinen
 *
 */
public class QuestionSet {

    public static void validateOptionAnswers(QuestionSet questionSet) {
        for (Question question : questionSet.getQuestions()) {
            if ((question.getType() == QuestionType.CHECKBOX) || (question.getType() == QuestionType.RADIOBUTTON)) {
                if (question.getAnswers().size() < 2) {
                    throw new IllegalArgumentException("Question with type checkbox or radiobutton need at least two answers!");
                }
            }
        }
    }

    private String uuid;
    private String text;
    private String description;
    private List<Question> questions = new ArrayList<Question>();
    private String submitButtonText;

    /**
     * Default constructor for QuestionSet
     */
    public QuestionSet() {
        super();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * Add question to set
     *
     * @param question
     */
    public void add(Question question) {
        questions.add(question);
    }

    public String getSubmitButtonText() {
        return submitButtonText;
    }

    public void setSubmitButtonText(String text) {
        this.submitButtonText = text;
    }

}
