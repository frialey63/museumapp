package org.pjp.museum.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vaadin.jarkjar.questionnaire.component.QuestionnaireComponent;
import com.vaadin.jarkjar.questionnaire.model.Question;
import com.vaadin.jarkjar.questionnaire.model.QuestionSet;
import com.vaadin.jarkjar.questionnaire.model.UserAnswer;
import com.vaadin.jarkjar.questionnaire.model.Question.QuestionType;

@Service
public class QuestionnaireService {

    public enum Category { MUSEUM, VISITOR }

    public QuestionSet getQuestionSet(Category category) {
        return switch(category) {
        case MUSEUM -> getMuseumQuestionSet();
        case VISITOR -> getVisitorQuestionSet();
        };
    }

    private QuestionSet getMuseumQuestionSet() {
        QuestionSet questionSet = new QuestionSet();
        questionSet.setText("The Museum");
        questionSet.setDescription("Some questions about your experience of the museum. Required anwsers are indicated by " + QuestionnaireComponent.LUMO_REQUIRED_INDICATOR);
        questionSet.setId(1);
        questionSet.setSubmitButtonText("Submit");

        int qid = 1;
        Question question;

        question = new Question(qid++, "How did you find out about the museum", QuestionType.CHECKBOX);
        question.setRequired(true);
        question.setRequiredError("Answer required");
        question.addAnswer("Web");
        question.addAnswer("Media");
        question.addAnswer("Friend");
        question.addAnswer("Museum");
        question.addAnswer("Other");
        questionSet.add(question);

        question = new Question(qid++, "The staff were friendly and helpful", QuestionType.RADIOBUTTON);
        question.setRequired(true);
        question.setRequiredError("Answer required");
        question.addAnswer("Strongly agree");
        question.addAnswer("Agree");
        question.addAnswer("Neutral");
        question.addAnswer("Disagree");
        question.addAnswer("Strongly disagree");
        questionSet.add(question);

        question = new Question(qid++, "The aircraft exhibits were interesting and relevant", QuestionType.RADIOBUTTON);
        question.setRequired(true);
        question.setRequiredError("Answer required");
        question.addAnswer("Strongly agree");
        question.addAnswer("Agree");
        question.addAnswer("Neutral");
        question.addAnswer("Disagree");
        question.addAnswer("Strongly disagree");
        questionSet.add(question);

        question = new Question(qid++, "The aircraft exhibits were well presented and explained", QuestionType.RADIOBUTTON);
        question.setRequired(true);
        question.setRequiredError("Answer required");
        question.addAnswer("Strongly agree");
        question.addAnswer("Agree");
        question.addAnswer("Neutral");
        question.addAnswer("Disagree");
        question.addAnswer("Strongly disagree");
        questionSet.add(question);

        question = new Question(qid++, "The static displays were interesting and relevant", QuestionType.RADIOBUTTON);
        question.setRequired(true);
        question.setRequiredError("Answer required");
        question.addAnswer("Strongly agree");
        question.addAnswer("Agree");
        question.addAnswer("Neutral");
        question.addAnswer("Disagree");
        question.addAnswer("Strongly disagree");
        questionSet.add(question);

        question = new Question(qid++, "The static displays were well presented and explained", QuestionType.RADIOBUTTON);
        question.setRequired(true);
        question.setRequiredError("Answer required");
        question.addAnswer("Strongly agree");
        question.addAnswer("Agree");
        question.addAnswer("Neutral");
        question.addAnswer("Disagree");
        question.addAnswer("Strongly disagree");
        questionSet.add(question);

        question = new Question(qid++, "The toilets were clean and pleasant to use", QuestionType.RADIOBUTTON);
        question.setRequired(false);
        question.addAnswer("Strongly agree");
        question.addAnswer("Agree");
        question.addAnswer("Neutral");
        question.addAnswer("Disagree");
        question.addAnswer("Strongly disagree");
        questionSet.add(question);

        question = new Question(qid++, "Suitable access was provided for the disabled, including wheelchair users", QuestionType.RADIOBUTTON);
        question.setRequired(false);
        question.addAnswer("Strongly agree");
        question.addAnswer("Agree");
        question.addAnswer("Neutral");
        question.addAnswer("Disagree");
        question.addAnswer("Strongly disagree");
        questionSet.add(question);

        question = new Question(qid++, "The entrance fee represented value for money", QuestionType.RADIOBUTTON);
        question.setRequired(true);
        question.setRequiredError("Answer required");
        question.addAnswer("Strongly agree");
        question.addAnswer("Agree");
        question.addAnswer("Neutral");
        question.addAnswer("Disagree");
        question.addAnswer("Strongly disagree");
        questionSet.add(question);

        question = new Question(qid++, "We / I am likely to re-visit the museum", QuestionType.RADIOBUTTON);
        question.setRequired(true);
        question.setRequiredError("Answer required");
        question.addAnswer("Strongly agree");
        question.addAnswer("Agree");
        question.addAnswer("Neutral");
        question.addAnswer("Disagree");
        question.addAnswer("Strongly disagree");
        questionSet.add(question);

        question = new Question(qid++, "We / I am likely to recommend friends/relatives to visit the museum", QuestionType.RADIOBUTTON);
        question.setRequired(true);
        question.setRequiredError("Answer required");
        question.addAnswer("Strongly agree");
        question.addAnswer("Agree");
        question.addAnswer("Neutral");
        question.addAnswer("Disagree");
        question.addAnswer("Strongly disagree");
        questionSet.add(question);

        question = new Question(qid++, "What did you like the most about the museum?", QuestionType.TEXTAREA);
        question.setAnswerMaxLength(4_000);
        question.setRequired(true);
        question.setRequiredError("Answer required");
        questionSet.add(question);

        question = new Question(qid++, "How could the museum best be improved?", QuestionType.TEXTAREA);
        question.setAnswerMaxLength(4_000);
        question.setRequired(true);
        question.setRequiredError("Answer required");
        questionSet.add(question);

        return questionSet;
    }

    private QuestionSet getVisitorQuestionSet() {
        QuestionSet questionSet = new QuestionSet();
        questionSet.setText("Our Visitor");
        questionSet.setDescription("Some questions about you and / or your group. All of these are optional");
        questionSet.setId(1);
        questionSet.setSubmitButtonText("Submit");

        int qid = 1;
        Question question;

        question = new Question(qid++, "Type", QuestionType.RADIOBUTTON);
        question.addAnswer("Individual");
        question.addAnswer("Family");
        question.addAnswer("Group");
        question.setRequired(false);
        questionSet.add(question);

        question = new Question(qid++, "Gender (Mix)", QuestionType.CHECKBOX);
        question.addAnswer("Male");
        question.addAnswer("Female");
        question.setRequired(false);
        questionSet.add(question);

        question = new Question(qid++, "Age (Mix)", QuestionType.CHECKBOX);
        question.addAnswer("under 7");
        question.addAnswer("7 - 11");
        question.addAnswer("12 - 18");
        question.addAnswer("19 - 67");
        question.addAnswer("over 67");
        question.setRequired(false);
        questionSet.add(question);

        question = new Question(qid++, "Ethnicity (Mix)", QuestionType.CHECKBOX);
        question.addAnswer("Asian");
        question.addAnswer("Black");
        question.addAnswer("Mixed");
        question.addAnswer("White");
        question.addAnswer("Other");
        question.setRequired(false);
        questionSet.add(question);

        question = new Question(qid++, "Disability (Mix)", QuestionType.CHECKBOX);
        question.addAnswer("Hard of hearing");
        question.addAnswer("Visually impaired");
        question.addAnswer("Wheelchair");
        question.setRequired(false);
        questionSet.add(question);

        Question q5 = new Question(5, "Nationality (Mix)", QuestionType.CHECKBOX);
        q5.addAnswer("UK");
        q5.addAnswer("France");
        q5.addAnswer("Europe");
        q5.addAnswer("Other");
        question.setRequired(false);
        questionSet.add(q5);

        return questionSet;
    }

    public void saveUserAnswers(List<UserAnswer> answers) {

    }
}
