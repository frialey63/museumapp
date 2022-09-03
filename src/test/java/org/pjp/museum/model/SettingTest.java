package org.pjp.museum.model;

import java.beans.IntrospectionException;

import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;
import nl.jqno.equalsverifier.EqualsVerifier;

class SettingTest {

    @Test
    public void testBeanProperties() throws IntrospectionException{
        JavaBeanTester.test(Setting.class);
    }

    @Test
    void testHashCode() {
        EqualsVerifier.simple().forClass(Setting.class).verify();
    }

    @Test
    void testEqualsObject() {
        EqualsVerifier.simple().forClass(Setting.class).verify();
    }

}
