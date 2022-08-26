package org.pjp.museum.service;

import java.time.LocalTime;

import org.pjp.museum.model.Setting;
import org.pjp.museum.repository.SettingRepository;
import org.pjp.museum.ui.collab.Broadcaster;
import org.pjp.museum.ui.collab.MuseumMessage;
import org.pjp.museum.ui.collab.MuseumMessage.MessageType;
import org.pjp.museum.util.UuidStr;
import org.springframework.stereotype.Service;

@Service
public class MuseumService {

    private static final int TEN_MINUTES = 10;

    private static final int THIRTY_MINUTES = 30;

    private final SettingRepository repository;

    public MuseumService(SettingRepository repository) {
        super();
        this.repository = repository;
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

    public void testData() {
        repository.deleteAll();

        Setting setting = new Setting(UuidStr.random(), Setting.CLOSING_TIME, "16:00");
        repository.save(setting);

    }
}
