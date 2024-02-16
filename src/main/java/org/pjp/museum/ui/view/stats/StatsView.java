package org.pjp.museum.ui.view.stats;

import java.util.Map;

import org.pjp.museum.model.AddressType;
import org.pjp.museum.model.MobileType;
import org.pjp.museum.model.Period;
import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.service.SessionRecordService;
import org.pjp.museum.ui.bean.Statistic;
import org.springframework.beans.factory.annotation.Value;
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

    private static VerticalLayout getUsageStatisticsLayout(Table table, String labelText) {
        Label label = new Label(labelText);

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

    private static VerticalLayout getExhibitStatisticsLayout(Table table) {
        Label label = new Label("The exhibit (scan/pick) statistics for various periods.");

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

    private static void addDataCell(TableRow detailsRow, String text) {
        TableCell cell = detailsRow.addDataCell();
        cell.setText(text);
        cell.getStyle().set("text-align", "center");
    }

    @Value("${secure.addresses}")
    private String secureAddresses;

    private final ExhibitService exhibitService;

    private final SessionRecordService sessionRecordService;

    private final Table usageTable = new Table();

    private final Table addressTable = new Table();

    private final Table exhibitScansTable = new Table();

    private final Table exhibitPicksTable = new Table();

    private final H1 heading1 = new H1("Museum App");
    private final H2 heading2 = new H2("Statistics");

    public StatsView(ExhibitService exhibitService, SessionRecordService sessionRecordService) {
        super();
        this.exhibitService = exhibitService;
        this.sessionRecordService = sessionRecordService;

        Accordion accordion = new Accordion();

        AccordionPanel usagePanel = accordion.add("Usage Statistics", getUsageStatisticsLayout(usageTable, "The app usage statistics for various periods."));
        usagePanel.addThemeVariants(DetailsVariant.FILLED);

        AccordionPanel addressPanel = accordion.add("Address Statistics", getUsageStatisticsLayout(addressTable, "The app address statistics for various periods."));
        addressPanel.addThemeVariants(DetailsVariant.FILLED);

        AccordionPanel tailNumberScansPanel = accordion.add("Exhibit (Scans) Statistics", getExhibitStatisticsLayout(exhibitScansTable));
        tailNumberScansPanel.addThemeVariants(DetailsVariant.FILLED);

        AccordionPanel tailNumberPicksPanel = accordion.add("Exhibit (Picks) Statistics", getExhibitStatisticsLayout(exhibitPicksTable));
        tailNumberPicksPanel.addThemeVariants(DetailsVariant.FILLED);

        setHorizontalComponentAlignment(Alignment.START, heading1, heading2);
        setHorizontalComponentAlignment(Alignment.STRETCH, accordion);
        setWidth("98%");

        add(heading1, heading2, accordion);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        populateUsageStatistics();

        populateAddressStatistics();

        populateExhibitStatistics(exhibitScansTable, true);

        populateExhibitStatistics(exhibitPicksTable, false);
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

            addDataCell(detailsRow, Integer.toString(statistic.getTotalCount()));
        }
    }

    private void populateAddressStatistics() {
        Map<Period, Statistic<AddressType>> statistics = sessionRecordService.compileAddressStatistics(secureAddresses);

        for (AddressType adressType : AddressType.values()) {
            TableRow detailsRow = addressTable.addRow();

            detailsRow.addDataCell().setText(adressType.toString());
            for (Period period : Period.values()) {
                Statistic<AddressType> statistic = statistics.get(period);

                addDataCell(detailsRow, Integer.toString(statistic.getCount(adressType)));
            }
        }

        TableRow detailsRow = addressTable.addRow();

        detailsRow.addDataCell().setText("Total");
        for (Period period : Period.values()) {
            Statistic<AddressType> statistic = statistics.get(period);

            addDataCell(detailsRow, Integer.toString(statistic.getTotalCount()));
        }
    }

    private void populateExhibitStatistics(Table table, boolean scan) {
        Map<Period, Statistic<String>> statistics = sessionRecordService.compileExhibitStatistics(scan);

        exhibitService.findAllExhibits().stream().forEach(exhibit -> {
            TableRow detailsRow = table.addRow();

            String tailNumber = exhibit.getTailNumber();

            detailsRow.addDataCell().setText(tailNumber);
            for (Period period : Period.values()) {
                Statistic<String> statistic = statistics.get(period);

                addDataCell(detailsRow, Integer.toString(statistic.getCount(tailNumber)));
            }
        });

        TableRow detailsRow = table.addRow();

        detailsRow.addDataCell().setText("Total");
        for (Period period : Period.values()) {
            Statistic<String> statistic = statistics.get(period);

            addDataCell(detailsRow, Integer.toString(statistic.getTotalCount()));
        }
    }

}
