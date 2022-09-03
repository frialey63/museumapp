package org.pjp.museum.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.pjp.museum.model.Setting;
import org.pjp.museum.repository.SettingRepository;
import org.pjp.museum.service.MuseumServiceTest.MuseumServiceTestContextConfiguration;
import org.pjp.museum.ui.collab.Broadcaster;
import org.pjp.museum.ui.collab.MuseumMessage.MessageType;
import org.pjp.museum.util.UuidStr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vaadin.flow.shared.Registration;

@ExtendWith(SpringExtension.class)
@Import(MuseumServiceTestContextConfiguration.class)
class MuseumServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumServiceTest.class);

    @TestConfiguration
    static class MuseumServiceTestContextConfiguration {

        @Autowired
        private SettingRepository repository;

        @Bean
        public MuseumService museumService() {
            return new MuseumService(repository);
        }
    }

    @Autowired
    private MuseumService service;

    @MockBean
    private SettingRepository repository;

    @Test
    void testGetClosingTime() {
        // GIVEN

        String name = Setting.CLOSING_TIME;
        String value = "16:00";

        Setting closingTime = new Setting(UuidStr.random(), name, value);

        // WHEN

        Mockito.when(repository.findByName(name)).thenReturn(Optional.of(closingTime));

        // THEN

        assertEquals(value, service.getClosingTime());
    }

    @Tag("slow-test")
    @Test
    void testCheckForAndNotifyClosingTimeClosed() throws InterruptedException {

        Registration broadcasterRegistration = null;

        try {
            final AtomicBoolean flag = new AtomicBoolean();

            broadcasterRegistration = Broadcaster.register(l -> {
                LOGGER.info("received the broadcast! {}", l.toString());

                if (l.messageType() == MessageType.CLOSED) {
                    flag.set(true);
                }
            });

            // GIVEN

            String name = Setting.CLOSING_TIME;
            String value = LocalTime.now().plusMinutes(1).format(DateTimeFormatter.ofPattern("HH:mm"));

            LOGGER.info("closing time = {}", value);

            Setting closingTime = new Setting(UuidStr.random(), name, value);

            // WHEN

            Mockito.when(repository.findByName(name)).thenReturn(Optional.of(closingTime));

            // THEN

            LOGGER.info("waiting one minute for broadcast...");
            for (int i = 0; i < 60; i++) {
                service.checkForAndNotifyClosingTime();

                if (flag.get()) {
                    break;
                }

                Thread.sleep(1_000);
            }

            assertTrue(flag.get(), "no broadcast of the closing time was detected");
        } finally {
            broadcasterRegistration.remove();
        }
    }

}
