package org.pjp.museum.ui.collab;

public record MuseumMessage(MessageType messageType, int minutes) {

    public enum MessageType { CLOSING_TIME, CLOSED }

}
