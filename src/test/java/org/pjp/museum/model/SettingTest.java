package org.pjp.museum.model;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class SettingTest {

    @Test
    void testHashCode() {
        EqualsVerifier.simple().forClass(Setting.class).verify();
    }

    @Test
    void testEqualsObject() {
        EqualsVerifier.simple().forClass(Setting.class).verify();
    }

}
