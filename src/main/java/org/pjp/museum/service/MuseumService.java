package org.pjp.museum.service;

import java.time.LocalTime;

import org.pjp.museum.model.Setting;
import org.pjp.museum.repository.SettingRepository;
import org.pjp.museum.ui.collab.Broadcaster;
import org.pjp.museum.ui.collab.MuseumMessage;
import org.pjp.museum.ui.collab.MuseumMessage.MessageType;
import org.pjp.museum.util.UuidStr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MuseumService {

    public static final String FOUR_PM = "16:00";
    public static final String THREE_PM = "15:00";

    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumService.class);

    private static final int TEN_MINUTES = 10;

    private static final int THIRTY_MINUTES = 30;

    private final SettingRepository repository;

    public MuseumService(SettingRepository repository) {
        super();
        this.repository = repository;
    }

    public String getClosingTime() {
        final StringBuffer result = new StringBuffer();

        repository.findByName(Setting.CLOSING_TIME).ifPresent(setting -> result.append(setting.getValue()));

        return result.toString();
    }

    public void updateClosingTime(String closingTime) {
        repository.findByName(Setting.CLOSING_TIME).ifPresent(setting -> {
            if (FOUR_PM.equals(closingTime)) {
                setting.setValue(FOUR_PM);
            } else if (THREE_PM.equals(closingTime)) {
                setting.setValue(THREE_PM);
            } else {
                throw new IllegalArgumentException("illegal closing time: " + closingTime);
            }

            LOGGER.info("updating museum closing time to " + closingTime);
            repository.save(setting);
        });
    }

    public void checkForAndNotifyClosingTime() {
        LocalTime now = LocalTime.now();

        repository.findByName(Setting.CLOSING_TIME).ifPresent(closingTimeStr -> {
            LocalTime closingTime = LocalTime.parse((String) closingTimeStr.getValue());

            if ((closingTime.getHour() == now.getHour()) && (closingTime.getMinute() == now.getMinute())) {
                Broadcaster.broadcast(new MuseumMessage(MessageType.CLOSED, 0));
            }

            LocalTime tenMinutesBefore = closingTime.minusMinutes(TEN_MINUTES);

            if ((tenMinutesBefore.getHour() == now.getHour()) && (tenMinutesBefore.getMinute() == now.getMinute())) {
                Broadcaster.broadcast(new MuseumMessage(MessageType.CLOSING_TIME, TEN_MINUTES));
            }

            LocalTime halfHourBefore = closingTime.minusMinutes(THIRTY_MINUTES);

            if ((halfHourBefore.getHour() == now.getHour()) && (halfHourBefore.getMinute() == now.getMinute())) {
                Broadcaster.broadcast(new MuseumMessage(MessageType.CLOSING_TIME, THIRTY_MINUTES));
            }
        });
    }

    public void initData() {
        if (repository.count() == 0) {
            LOGGER.info("initialising the museum repository with Setting");

            Setting setting = new Setting(UuidStr.random(), Setting.CLOSING_TIME, FOUR_PM);
            repository.save(setting);
        }
    }
}
