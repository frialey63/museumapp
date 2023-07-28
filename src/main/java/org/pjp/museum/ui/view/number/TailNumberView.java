package org.pjp.museum.ui.view.number;

import org.apache.logging.log4j.util.Strings;
import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.service.SessionRecordService;
import org.pjp.museum.service.bean.TailNumber;
import org.pjp.museum.ui.util.AddressUtils;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.exhibit.ExhibitView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Enter Tail Number")
@Route(value = "tail", layout = MainLayout.class)
public class TailNumberView extends VerticalLayout implements AfterNavigationObserver, ValueChangeListener<ComponentValueChangeEvent<ListBox<TailNumber>, TailNumber>> {

    private static final long serialVersionUID = 2220031256723181930L;

    private static final Logger LOGGER = LoggerFactory.getLogger(TailNumberView.class);
    
    private final ListBox<TailNumber> listBox = new ListBox<>();

    @Value("${secure.addresses}")
    private String secureAddresses;

    private final ExhibitService exhibitService;
    
    private final SessionRecordService sessionRecordService;

    public TailNumberView(ExhibitService exhibitService, SessionRecordService sessionRecordService) {
        super();
        this.exhibitService = exhibitService;
        this.sessionRecordService = sessionRecordService;

        Paragraph helpText = new Paragraph("In case the QR Code scanner is not working, it is possible to load the exhibit information by selecting the relevant aircraft tail number from the list below. (Use the Settings page to set this mode as default.)");

        setHorizontalComponentAlignment(Alignment.START, helpText);
        add(helpText);

        listBox.addValueChangeListener(this);
        listBox.setHeightFull();

        setHorizontalComponentAlignment(Alignment.CENTER, listBox);
        add(listBox);

        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
    	if (isAllowed(UI.getCurrent())) {
    		listBox.setItems(exhibitService.getTailNumbers());
    	}
    }

    @Override
    public void valueChanged(ComponentValueChangeEvent<ListBox<TailNumber>, TailNumber> event) {
    	String tailNumber = event.getValue().tailNumber();
    	
		sessionRecordService.updateRecord(VaadinSession.getCurrent(), tailNumber, false);
        UI.getCurrent().navigate(ExhibitView.class, tailNumber);
    }
    
    private boolean isAllowed(UI ui) {
    	if (Strings.isNotEmpty(secureAddresses)) {
        	String ipAddress = AddressUtils.getRealAddress(ui.getSession());
            LOGGER.debug("IP address = {}", ipAddress);
           
        	return AddressUtils.checkAddressIsSecure(secureAddresses, ipAddress);
    	}
    	
    	return true;
    }

}
