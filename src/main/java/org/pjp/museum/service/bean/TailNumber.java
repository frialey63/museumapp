package org.pjp.museum.service.bean;

public record TailNumber(String tailNumber, String uuid) implements Comparable<TailNumber> {

    @Override
    public String toString() {
        return tailNumber;
    }

    @Override
    public int compareTo(TailNumber o) {
        return tailNumber.compareTo(o.tailNumber);
    }
}