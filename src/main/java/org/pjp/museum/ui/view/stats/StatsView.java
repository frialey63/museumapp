package org.pjp.museum.ui.view.stats;

import java.util.Map;

import org.pjp.museum.model.Exhibit;
import org.pjp.museum.model.MobileType;
import org.pjp.museum.model.Period;
import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.service.SessionRecordService;
import org.pjp.museum.ui.bean.Statistic;
import org.vaadin.stefan.table.Table;
import org.vaadin.stefan.table.TableCell;
import org.vaadin.stefan.table.TableRow;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Stats")
public class StatsView extends VerticalLayout implements AfterNavigationObserver {

	private static final long serialVersionUID = 8363550976566676150L;

	private static void addDataCell(TableRow detailsRow, String text) {
		TableCell cell = detailsRow.addDataCell();
        cell.setText(text);
        cell.getStyle().set("text-align", "center");
	}

    private final ExhibitService exhibitService;
    
    private final SessionRecordService sessionRecordService;
    
    private final Table usageTable = new Table();
    
    private final Table tailNumberScansTable = new Table();
    
    private final Table tailNumberPicksTable = new Table();
    
    private final H1 heading1 = new H1("Museum App");
    private final H2 heading2 = new H2("Statistics");
    
    public StatsView(ExhibitService exhibitService, SessionRecordService sessionRecordService) {
        super();
        this.exhibitService = exhibitService;
        this.sessionRecordService = sessionRecordService;
        
        Accordion accordion = new Accordion();
        
        AccordionPanel usagePanel = accordion.add("Usage Statistics", getUsageStatisticsLayout());
        usagePanel.addThemeVariants(DetailsVariant.FILLED);
        
        AccordionPanel tailNumberScansPanel = accordion.add("Tail Number (Scans) Statistics", getTailNumberStatisticsLayout(tailNumberScansTable));
        tailNumberScansPanel.addThemeVariants(DetailsVariant.FILLED);
        
        AccordionPanel tailNumberPicksPanel = accordion.add("Tail Number (Picks) Statistics", getTailNumberStatisticsLayout(tailNumberPicksTable));
        tailNumberPicksPanel.addThemeVariants(DetailsVariant.FILLED);
        
        setHorizontalComponentAlignment(Alignment.START, heading1, heading2);
        setHorizontalComponentAlignment(Alignment.STRETCH, accordion);
        setWidth("98%");
        
        add(heading1, heading2, accordion);
    }

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
    	populateUsageStatistics();
        
    	populateTailNumberStatistics(tailNumberScansTable, true);
        
    	populateTailNumberStatistics(tailNumberPicksTable, false);
	}

	private void populateUsageStatistics() {
		Map<Period, Statistic<MobileType>> statistics = sessionRecordService.compileUsageStatistics();
        
        for (MobileType mobileType : MobileType.values()) {
            TableRow detailsRow = usageTable.addRow();
            
            detailsRow.addDataCell().setText(mobileType.toString());
            for (Period period : Period.values()) {
                Statistic<MobileType> statistic = statistics.get(period);
    			
    			addDataCell(detailsRow, Integer.toString(statistic.getCount(mobileType)));
            }
		}
        
        TableRow detailsRow = usageTable.addRow();
        
        detailsRow.addDataCell().setText("Total");
        for (Period period : Period.values()) {
            Statistic<MobileType> statistic = statistics.get(period);
			
            addDataCell(detailsRow, Integer.toString(statistic.getTotalCount(null)));
        }
	}

	private void populateTailNumberStatistics(Table table, boolean scan) {
		Map<Period, Statistic<String>> statistics = sessionRecordService.compileTailNumberStatistics(scan);
        
		exhibitService.getTailNumbers().stream().forEach(tailNumber -> {
            TableRow detailsRow = table.addRow();
            
            detailsRow.addDataCell().setText(tailNumber.tailNumber());
            for (Period period : Period.values()) {
                Statistic<String> statistic = statistics.get(period);
    			
    			addDataCell(detailsRow, Integer.toString(statistic.getCount(tailNumber.tailNumber())));
            }
		});
        
        TableRow detailsRow = table.addRow();
        
        detailsRow.addDataCell().setText("Total");
        for (Period period : Period.values()) {
            Statistic<String> statistic = statistics.get(period);
			
            addDataCell(detailsRow, Integer.toString(statistic.getTotalCount(Exhibit.MUSEUM)));
        }
	}

    private VerticalLayout getUsageStatisticsLayout() {
        Label label = new Label("The app usage statistics for various periods.");
        
        TableRow headerRow = usageTable.addRow();
        
        headerRow.addHeaderCell().setText("");
        for (Period period : Period.values()) {
            headerRow.addHeaderCell().setText(period.name());
        }

        usageTable.setWidth("80%");
        
        VerticalLayout vl = new VerticalLayout(label, usageTable);
        vl.setHorizontalComponentAlignment(Alignment.START, label, usageTable);
        vl.setMargin(true);
        
        return vl;
    }
    
    
    
	private VerticalLayout getTailNumberStatisticsLayout(Table table) {
		Label label = new Label("The tail number (scan/pick) statistics for various periods.");

        TableRow headerRow = table.addRow();
        
        headerRow.addHeaderCell().setText("");
        for (Period period : Period.values()) {
            headerRow.addHeaderCell().setText(period.name());
        }

        table.setWidth("80%");
        
        VerticalLayout vl = new VerticalLayout(label, table);
        vl.setHorizontalComponentAlignment(Alignment.START, label, table);
        vl.setMargin(true);
        
        return vl;
	}
    
}
