package org.pjp.museum.service;

import java.time.Instant;

import org.pjp.museum.model.MobileType;
import org.pjp.museum.model.SessionRecord;
import org.pjp.museum.repository.SessionRecordRepository;
import org.pjp.museum.ui.util.AddressUtils;
import org.springframework.stereotype.Service;

import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;
import com.vaadin.flow.server.WrappedSession;

@Service
public class SessionRecordService {

    private final SessionRecordRepository repository;

    public SessionRecordService(SessionRecordRepository repository) {
        super();
        this.repository = repository;
    }

    public void createRecord(VaadinSession vaadinSession) {
        WrappedSession wrappedSession = vaadinSession.getSession();

        String ipAddress = AddressUtils.getRealAddress(vaadinSession);

        WebBrowser browser = vaadinSession.getBrowser();
		SessionRecord sessionRecord = new SessionRecord(wrappedSession.getId(), ipAddress, browser.getBrowserApplication(), MobileType.get(browser), Instant.now());
		repository.save(sessionRecord);
    }

    public void finaliseRecord(VaadinSession vaadinSession) {
        WrappedSession wrappedSession = vaadinSession.getSession();

        repository.findById(wrappedSession.getId()).ifPresent(sessionRecord -> {
        	sessionRecord.setFinishTime(Instant.now());
        	repository.save(sessionRecord);
        });
    }
    
    public void updateRecord(VaadinSession vaadinSession, String tailNumber, boolean scan) {
        WrappedSession wrappedSession = vaadinSession.getSession();

        repository.findById(wrappedSession.getId()).ifPresent(sessionRecord -> {
        	if (scan) {
        		sessionRecord.addTailScan(tailNumber);
        	} else {
        		sessionRecord.addTailPick(tailNumber);
        	}
        	
        	repository.save(sessionRecord);
        });
    }
}