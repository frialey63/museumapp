package org.pjp.museum.model;

import java.beans.IntrospectionException;

import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;
import nl.jqno.equalsverifier.EqualsVerifier;

class ExhibitTest {

    @Test
    public void testBeanProperties() throws IntrospectionException{
        JavaBeanTester.test(Exhibit.class);
    }

    @Test
    void testHashCode() {
        EqualsVerifier.simple().forClass(Exhibit.class).withIgnoredFields("displayOrder", "name", "tailNumber", "description", "imageFile", "audioFile").verify();
    }

    @Test
    void testEqualsObject() {
        EqualsVerifier.simple().forClass(Exhibit.class).withIgnoredFields("displayOrder", "name", "tailNumber", "description", "imageFile", "audioFile").verify();
    }

}
