package org.pjp.museum.service.bean;

public record TailNumber(String tailNumber, String uuid) {

    @Override
    public String toString() {
        return tailNumber;
    }

}