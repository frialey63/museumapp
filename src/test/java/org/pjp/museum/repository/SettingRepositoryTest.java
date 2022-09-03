package org.pjp.museum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pjp.museum.model.Setting;
import org.pjp.museum.util.UuidStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class SettingRepositoryTest {

    @Autowired
    private SettingRepository repository;

    private Setting setting;

    @BeforeEach
    void beforeEach() {
        setting = new Setting(UuidStr.random(), "myName", 999);

        repository.save(setting);
    }

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @Test
    void testFindByNameExists() {
        Optional<Setting> optSetting = repository.findByName("myName");

        assertTrue(optSetting.isPresent());
        assertEquals(setting, optSetting.get());
    }

    @Test
    void testFindByNameNonExistent() {
        Optional<Setting> optSetting = repository.findByName("anyName");

        assertFalse(optSetting.isPresent());
    }

}
