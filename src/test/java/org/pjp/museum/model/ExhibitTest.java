package org.pjp.museum.model;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class ExhibitTest {

    @Test
    void testHashCode() {
        EqualsVerifier.simple().forClass(Exhibit.class).withIgnoredFields("displayOrder", "name", "tailNumber", "description", "imageFile", "audioFile").verify();
    }

    @Test
    void testEqualsObject() {
        EqualsVerifier.simple().forClass(Exhibit.class).withIgnoredFields("displayOrder", "name", "tailNumber", "description", "imageFile", "audioFile").verify();
    }

}
