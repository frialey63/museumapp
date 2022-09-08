package com.vaadin.jarkjar.questionnaire.model;

import java.time.Instant;
import java.util.List;

/**
 * @author Paul Parlett
 *
 */
public record UserAnswerSet(String uuid, String questionSetUuid, List<UserAnswer> userAnswers, Instant dateTime, String sessionId) {
}
