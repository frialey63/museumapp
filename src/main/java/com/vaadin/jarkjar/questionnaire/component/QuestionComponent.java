package com.vaadin.jarkjar.questionnaire.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.pjp.museum.ui.component.CompactHorizontalLayout;
import org.pjp.museum.ui.component.CompactVerticalLayout;

import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.jarkjar.questionnaire.model.Question;
import com.vaadin.jarkjar.questionnaire.model.UserAnswer;

/**
 * Represents Question item in QuestionnaireWidget. Wraps all fields used in
 * question. Handle user answer returning functionality.
 *
 * @author Jarkko Järvinen
 * @author Paul Parlett
 *
 */
public class QuestionComponent extends CompactVerticalLayout {

    private static final long serialVersionUID = 3231017557951720771L;

    public static class MultiCheckboxField extends AbstractCompositeField<CompactVerticalLayout, MultiCheckboxField, String> {
        private static final long serialVersionUID = -8566712597298599657L;

        private final List<Checkbox> checkBoxes = new ArrayList<>();

        final Label statusLabel = new Label();
        {
            statusLabel.getStyle().set("font-size", "var(--lumo-font-size-xs)");
            statusLabel.getStyle().set("line-height", "var(--lumo-line-height-xs)");
            statusLabel.getStyle().set("color", "var(--lumo-error-text-color)");
        }

        public MultiCheckboxField(Question question) {
            super(null);

            Label label = new Label(question.getText() + (question.isRequired() ? " " + QuestionnaireComponent.LUMO_REQUIRED_INDICATOR : ""));
            label.getStyle().set("font-size", "var(--lumo-font-size-s)");
            label.getStyle().set("font-weight", "500");
            label.getStyle().set("line-height", "var(--lumo-line-height-s)");
            label.getStyle().set("color", "var(--lumo-secondary-text-color)");

            HorizontalLayout hl = new CompactHorizontalLayout();

            for (String answer : question.getAnswers()) {
                Checkbox checkBox = new Checkbox(answer);
                checkBox.addValueChangeListener(l -> {
                    setModelValue(getPresentationValue(), false);
                });
                checkBoxes.add(checkBox);

                hl.add(checkBox);
            }

            getContent().add(label, hl, statusLabel);
        }

        public void setRequired(boolean required) {
            // nothing to do
        }

        private String getPresentationValue() {
            StringBuilder stringBuilder = new StringBuilder();

            checkBoxes.forEach(checkBox -> {
                if (checkBox.getValue()) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(checkBox.getLabel());
                }
            });

            return stringBuilder.toString();
        }

        @Override
        protected void setPresentationValue(String newPresentationValue) {
            // TODO unwrap the formatted string and set the checkboxes
        }
    }

    private static boolean validate(boolean required, String value) {
        return required ? ((value != null) && !value.isEmpty()) : true;
    }

    private Question question;

    private UserAnswer userAnswer;

    private Binder<UserAnswer> binder = new Binder<>(UserAnswer.class);

    public QuestionComponent(Question question) {
        this.question = question;
        this.userAnswer = new UserAnswer(question.getId());

        boolean required = question.isRequired();

        // Set answer fields
        switch (question.getType()) {
        case TEXTFIELD:
            TextField textField = new TextField(question.getText());
            textField.setWidthFull();
            textField.setMaxLength(question.getAnswerMaxLength());
            textField.setRequired(required);
            binder.forField(textField).withValidator(value -> validate(required, value), question.getRequiredError()).bind(UserAnswer::getValue, UserAnswer::setValue);
            add(textField);
            break;
        case TEXTAREA:
            TextArea textArea = new TextArea(question.getText());
            textArea.setWidthFull();
            textArea.setMaxLength(question.getAnswerMaxLength());
            textArea.setRequired(required);
            binder.forField(textArea).withValidator(value -> validate(required, value), question.getRequiredError()).bind(UserAnswer::getValue, UserAnswer::setValue);
            add(textArea);
            break;
        case CHECKBOX:
            MultiCheckboxField multiCheckboxField = new MultiCheckboxField(question);
            multiCheckboxField.setRequired(required);
            binder.forField(multiCheckboxField).withStatusLabel(multiCheckboxField.statusLabel).withValidator(value -> validate(required, value), question.getRequiredError()).bind(UserAnswer::getValue, UserAnswer::setValue);
            add(multiCheckboxField);
            break;
        case RADIOBUTTON:
            RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>(question.getText());
            radioGroup.setItems(question.getAnswers());
            radioGroup.setRequired(required);
            binder.forField(radioGroup).withValidator(value -> validate(required, value), question.getRequiredError()).bind(UserAnswer::getValue, UserAnswer::setValue);
            add(radioGroup);
            break;
        default:
            break;
        }

        binder.readBean(userAnswer);
    }

    /**
     * @return the question id
     */
    public int getQuestionId() {
        return question.getId();
    }

    public Optional<UserAnswer> getUserAnswer() {
        BinderValidationStatus<UserAnswer> status = binder.validate();

        if (status.isOk()) {
            try {
                binder.writeBean(userAnswer);
                return Optional.of(userAnswer);
            } catch (ValidationException e) {
                // nothing to do
            }
        }

        return Optional.empty();
    }
}
