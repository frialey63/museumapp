package com.vaadin.jarkjar.questionnaire.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents single question in questionnaire
 *
 * @author Jarkko Järvinen
 *
 */
public class Question implements Serializable {

    private static final long serialVersionUID = -5868558554332257008L;

    private static final int DEFAULT_TEXTFIELD_MAX_VALUE = 255;
    private static final int DEFAULT_TEXTAREA_MAX_VALUE = 4000;
    private static final String DEFAULT_REQUIRED_INDICATOR = "*";
    private static final String DEFAULT_REQUIRED_ERROR = "Required!";
    private String defaultMaxLengthError = "Max length is ";

    private int id;
    private String text;
    private QuestionType type;
    private List<String> answers;
    private boolean required;
    private int answerMaxLength;
    private String requiredIndicator;
    private String requiredError;
    private String maxLengthError;

    /**
     * Question behavior in questionnaire
     *
     * @author Jarkko Järvinen
     *
     */
    public enum QuestionType implements Serializable {
        /**
         * TextBox will be used in UI
         */
        TEXTFIELD,
        /**
         * TextField will be used in UI
         */
        TEXTAREA,
        /**
         * CheckBoxes will be used in UI
         */
        CHECKBOX,
        /**
         * RadioButtons will be used in UI. This is question type is always
         * required.
         */
        RADIOBUTTON
    }

    /**
     * Constructor for Question which type is TEXTFIELD
     *
     * @param title
     */
    public Question(int id, String title) {
        this();
        setId(id);
        setText(title);
    }

    public Question(int id, String title, QuestionType type) {
        this();
        setId(id);
        setText(title);
        setType(type);
    }

    /**
     * Default constructor for Question which type is TEXTFIELD
     */
    public Question() {
        this.id = 0;
        this.type = QuestionType.TEXTFIELD;
        this.answers = new ArrayList<String>();
    }

    /**
     * @return the type
     */
    public QuestionType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(QuestionType type) {
        this.type = type;
    }

    /**
     * @return the text of the question
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to question
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Add single answer to the question
     *
     * @param answer
     */
    public void addAnswer(String answer) {
        this.answers.add(answer);
    }

    /**
     * Set answers to the question
     *
     * @param answers
     */
    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    /**
     * Returns all answers of the question
     *
     * @return
     */
    public List<String> getAnswers() {
        return answers;
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
     * @return the required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @param required
     *            the required to set
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * Return user answer max length. If not set then default values are for
     * TextField 255 characters or for TextArea 4000 characters
     *
     * @return
     */
    public int getAnswerMaxLength() {
        if (this.answerMaxLength == 0) {
            if (this.type == QuestionType.TEXTFIELD) {
                return Question.DEFAULT_TEXTFIELD_MAX_VALUE;
            } else if (this.type == QuestionType.TEXTAREA) {
                return Question.DEFAULT_TEXTAREA_MAX_VALUE;
            }
        }
        return this.answerMaxLength;
    }

    public void setAnswerMaxLength(int maxLength) {
        this.answerMaxLength = maxLength;
    }

    /**
     * @return the requiredIndicator
     */
    @Deprecated
    public String getRequiredIndicator() {
        if (this.requiredIndicator == null) {
            return Question.DEFAULT_REQUIRED_INDICATOR;
        }
        return requiredIndicator;
    }

    /**
     * @param indicator
     *            the requiredIndicator to set
     */
    @Deprecated
    public void setRequiredIndicator(String indicator) {
        this.requiredIndicator = indicator;
    }

    /**
     * @return the requiredError
     */
    public String getRequiredError() {
        if (this.requiredError == null) {
            return Question.DEFAULT_REQUIRED_ERROR;
        }
        return requiredError;
    }

    /**
     * Set required error message
     *
     * @param requiredErrorMessage
     */
    public void setRequiredError(String requiredErrorMessage) {
        this.requiredError = requiredErrorMessage;
    }

    /**
     * Set maximum length error message
     *
     * @param maxLengthError
     */
    public void setMaxLengthError(String maxLengthErrorMessage) {
        this.maxLengthError = maxLengthErrorMessage;
    }

    /**
     * Get maximum length error message
     *
     * @return
     */
    public String getMaxLengthError() {
        if (this.maxLengthError == null) {
            return defaultMaxLengthError + this.getAnswerMaxLength();
        }
        return maxLengthError;
    }

}
