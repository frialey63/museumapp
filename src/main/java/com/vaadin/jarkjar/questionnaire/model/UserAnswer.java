package com.vaadin.jarkjar.questionnaire.model;

import java.io.Serializable;

public class UserAnswer implements Serializable {

    private static final long serialVersionUID = 585246572881301413L;

    private int questionId;
    private String value;

    public UserAnswer() {
    }

    public UserAnswer(int questionId, String value) {
        this.questionId = questionId;
        this.value = value;
    }

    public UserAnswer(int questionId) {
        this(questionId, null);
    }

    /**
     * @return the questionId
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId
     *            the questionId to set
     */
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserAnswer [questionId=");
        builder.append(questionId);
        builder.append(", value=");
        builder.append(value);
        builder.append("]");
        return builder.toString();
    }

}
