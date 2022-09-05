package com.vaadin.jarkjar.questionnaire.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.jarkjar.questionnaire.model.Question.QuestionType;

/**
 * Data source container for Questionnaire
 *
 * @author Jarkko Järvinen
 *
 */
public class QuestionSet implements Serializable {

    private static final long serialVersionUID = 500544663387309742L;

    public static void validateOptionAnswers(QuestionSet questionSet) {
        for (Question question : questionSet.getQuestions()) {
            if ((question.getType() == QuestionType.CHECKBOX) || (question.getType() == QuestionType.RADIOBUTTON)) {
                if (question.getAnswers().size() < 2) {
                    throw new IllegalArgumentException("Question with type checkbox or radiobutton need at least two answers!");
                }
            }
        }
    }

    private int id;
    private String text;
    private String description;
    private List<Question> questions;
    private String submitButtonText;

    public QuestionSet() {
        this.questions = new ArrayList<Question>();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the question
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * Add question to questionSet
     *
     * @param question
     */
    public void add(Question question) {
        this.questions.add(question);
    }

    /**
     * Set submit button text
     *
     * @param text
     */
    public void setSubmitButtonText(String text) {
        this.submitButtonText = text;
    }

    /**
     * @return the submitButtonText
     */
    public String getSubmitButtonText() {
        return this.submitButtonText;
    }

}
