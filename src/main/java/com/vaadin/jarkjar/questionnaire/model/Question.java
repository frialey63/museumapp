package com.vaadin.jarkjar.questionnaire.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents single question in questionnaire.
 *
 * @author Paul Parlett
 * @author Jarkko Järvinen
 *
 */
public class Question implements Comparable<Question> {

    private static final int DEFAULT_TEXTFIELD_MAX_VALUE = 255;

    private static final int DEFAULT_TEXTAREA_MAX_VALUE = 4000;

    private static final String DEFAULT_REQUIRED_ERROR = "Required!";

    private static final String DEFAULT_MAX_LENGTH_ERROR = "Max length is ";

    private int id;
    private String text;
    private QuestionType type;
    private List<String> answers = new ArrayList<String>();
    private boolean required;
    private int answerMaxLength;
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
         * RadioButtons will be used in UI.
         */
        RADIOBUTTON
    }

    /**
     * Constructor for Question which type is TEXTFIELD
     *
     * @param title the title
     */
    public Question(int id, String title) {
        this();
        setId(id);
        setText(title);
    }

    /**
     * Constructor for Question
     *
     * @param id the id
     * @param title the title
     * @param type the type
     */
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
        super();
        this.type = QuestionType.TEXTFIELD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    /**
     * Add single answer to the question
     *
     * @param answer
     */
    public void addAnswer(String answer) {
        answers.add(answer);
    }

    public boolean isRequired() {
        return required;
    }

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
        if (answerMaxLength == 0) {
            if (type == QuestionType.TEXTFIELD) {
                return Question.DEFAULT_TEXTFIELD_MAX_VALUE;
            } else if (this.type == QuestionType.TEXTAREA) {
                return Question.DEFAULT_TEXTAREA_MAX_VALUE;
            }
        }

        return answerMaxLength;
    }

    public void setAnswerMaxLength(int maxLength) {
        this.answerMaxLength = maxLength;
    }

    /**
     * @return the requiredError
     */
    public String getRequiredError() {
        if (requiredError == null) {
            return Question.DEFAULT_REQUIRED_ERROR;
        }

        return requiredError;
    }

    public void setRequiredError(String requiredErrorMessage) {
        this.requiredError = requiredErrorMessage;
    }

    /**
     * Get maximum length error message
     *
     * @return
     */
    public String getMaxLengthError() {
        if (maxLengthError == null) {
            return DEFAULT_MAX_LENGTH_ERROR + this.getAnswerMaxLength();
        }

        return maxLengthError;
    }

    public void setMaxLengthError(String maxLengthErrorMessage) {
        this.maxLengthError = maxLengthErrorMessage;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Question [id=");
        builder.append(id);
        builder.append(", text=");
        builder.append(text);
        builder.append(", type=");
        builder.append(type);
        builder.append(", answers=");
        builder.append(answers);
        builder.append(", required=");
        builder.append(required);
        builder.append(", answerMaxLength=");
        builder.append(answerMaxLength);
        builder.append(", requiredError=");
        builder.append(requiredError);
        builder.append(", maxLengthError=");
        builder.append(maxLengthError);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(Question o) {
        return Integer.valueOf(id).compareTo(Integer.valueOf(o.id));
    }

}
