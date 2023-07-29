package org.pjp.museum.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.pjp.museum.model.AddressType;
import org.pjp.museum.model.MobileType;
import org.pjp.museum.model.Period;
import org.pjp.museum.model.SessionRecord;
import org.pjp.museum.repository.SessionRecordRepository;
import org.pjp.museum.ui.bean.Statistic;
import org.pjp.museum.ui.util.AddressUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;
import com.vaadin.flow.server.WrappedSession;

@Service
public class SessionRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRecordService.class);

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
     
    public Map<Period, Statistic<MobileType>> compileUsageStatistics() {
    	Map<Period, Statistic<MobileType>> result = new HashMap<>();
    	
    	for (Period period : Period.values()) {
    		result.put(period, new Statistic<MobileType>(period));
		}

    	repository.findAll().forEach(sessionRecord -> {
    		LOGGER.debug(sessionRecord.toString());
    		
    		sessionRecord.getPeriods().forEach(period -> {
    			result.get(period).incCount(sessionRecord.getMobileType());
    		});
    	});
    	
    	return result;
    }

    public Map<Period, Statistic<AddressType>> compileAddressStatistics(String secureAddresses) {
    	Map<Period, Statistic<AddressType>> result = new HashMap<>();
    	
    	for (Period period : Period.values()) {
    		result.put(period, new Statistic<AddressType>(period));
		}

    	repository.findAll().forEach(sessionRecord -> {
    		LOGGER.debug(sessionRecord.toString());
    		
    		sessionRecord.getPeriods().forEach(period -> {
    			result.get(period).incCount(sessionRecord.getAddressType(secureAddresses));
    		});
    	});
    	
    	return result;
    }

	public Map<Period, Statistic<String>> compileExhibitStatistics(boolean scan) {
		Map<Period, Statistic<String>> result = new HashMap<>();
    	
    	for (Period period : Period.values()) {
    		result.put(period, new Statistic<String>(period));
		}

    	repository.findAll().forEach(sessionRecord -> {
    		LOGGER.debug(sessionRecord.toString());

    		sessionRecord.getPeriods().forEach(period -> {
    			sessionRecord.getTailNumbers(scan).forEach(tailNumber -> {
    				result.get(period).incCount(tailNumber);
    			});
    		});
    	});

		return result;
	}
    
}
