package org.pjp.museum.ui.view.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.pjp.museum.model.Exhibit;
import org.pjp.museum.model.MobileType;
import org.pjp.museum.model.Period;
import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.service.SessionRecordService;
import org.pjp.museum.ui.bean.Statistic;
import org.pjp.museum.ui.util.AudioUtils;
import org.pjp.museum.ui.util.FileUtils;
import org.pjp.museum.ui.util.ImageUtils;
import org.pjp.museum.ui.util.QrCodeUtils;
import org.pjp.museum.ui.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.vaadin.olli.FileDownloadWrapper;
import org.vaadin.stefan.table.Table;
import org.vaadin.stefan.table.TableCell;
import org.vaadin.stefan.table.TableRow;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

@PageTitle("Admin")
public class AdminView extends VerticalLayout implements AfterNavigationObserver {

    private static final long serialVersionUID = 3386437553156944523L;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminView.class);

	private static final File TMPDIR = new File(System.getProperty("java.io.tmpdir"));
	
    private static final String TEXT_EXTN = ".txt";

    private static final String IMAGE_EXTN = ".jpg";

    private static final String AUDIO_EXTN = ".wav";

    private static final String QR_EXTN = "-qrcode.png";

	private static void addDataCell(TableRow detailsRow, String text) {
		TableCell cell = detailsRow.addDataCell();
        cell.setText(text);
        cell.getStyle().set("text-align", "center");
	}

    static {
		LOGGER.info("tmpdir = {}", TMPDIR);
    }
    
    private final ExhibitService exhibitService;
    
    private final SessionRecordService sessionRecordService;
    
    @Value("${enable.csv-import:false}")
    private boolean enableCsvImport;
    
    private AccordionPanel csvImportPanel;

    private final MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();

    private final FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(null);

    private final IntegerField sizeField = new IntegerField();
    
    private final IntegerField fontSizeField = new IntegerField();
    
    private final Table table = new Table();
    
    public AdminView(ExhibitService exhibitService, SessionRecordService sessionRecordService) {
        super();
        this.exhibitService = exhibitService;
        this.sessionRecordService = sessionRecordService;
        
        Accordion accordion = new Accordion();
        
        AccordionPanel statsPanel = accordion.add("Statistics", getStatisticsLayout());
        statsPanel.addThemeVariants(DetailsVariant.FILLED);
        
        csvImportPanel = accordion.add("CSV Import", getCsvImportLayout());
        csvImportPanel.addThemeVariants(DetailsVariant.FILLED);
        
        AccordionPanel qrCodeGenerationPanel = accordion.add("QR Code Generation", getQrCodeGenerationLayout());
        qrCodeGenerationPanel.addThemeVariants(DetailsVariant.FILLED);
        qrCodeGenerationPanel.addOpenedChangeListener(l -> {
            sizeField.setValue(400);
            fontSizeField.setValue(40);
        });
        
        setHorizontalComponentAlignment(Alignment.STRETCH, accordion);
        setWidth("98%");
        
        add(accordion);
    }

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
    	Map<Period, Statistic> statistics = sessionRecordService.compileStatistics();
        
        for (Period period : Period.values()) {
            Statistic statistic = statistics.get(period);
			
            TableRow detailsRow = table.addRow();
            detailsRow.addDataCell().setText(period.name());
			addDataCell(detailsRow, Integer.toString(statistic.getCount(MobileType.ANDROID)));
            addDataCell(detailsRow, Integer.toString(statistic.getCount(MobileType.IPHONE)));
            addDataCell(detailsRow, Integer.toString(statistic.getCount(MobileType.WINDOWS_PHONE)));
            addDataCell(detailsRow, Integer.toString(statistic.getCount(MobileType.OTHER)));
            addDataCell(detailsRow, Integer.toString(statistic.getTotalCount()));
            detailsRow = table.addRow();
		}
        
        csvImportPanel.setEnabled(enableCsvImport);
	}

    private VerticalLayout getCsvImportLayout() {
        Label label = new Label("Select the CSV file containing the exhibits for import into MongoDB.");

        Upload upload = new Upload(buffer);
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);

            Reader in = new InputStreamReader(inputStream);

            try {
                @SuppressWarnings("deprecation")
                Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

                int count = 0;

                for (CSVRecord record : records) {
                    int displayOrder = Integer.parseInt(record.get("displayOrder"));
                    String name = record.get("name");
                    String tailNumber = record.get("tailNumber");
                    String baseFilename = record.get("baseFilename");
                    
                    String textFilename = baseFilename + TEXT_EXTN;
                    String imageFilename = baseFilename + IMAGE_EXTN;
                    String audioFilename = baseFilename + AUDIO_EXTN;
                    
                    if (TextUtils.existsAndReadable(textFilename) && AudioUtils.existsAndReadable(audioFilename) && ImageUtils.existsAndReadable(imageFilename)) {
                    	String qrCode = baseFilename + QR_EXTN;
                    	
                        Exhibit exhibit = new Exhibit(displayOrder, name, tailNumber, TextUtils.readText(textFilename), imageFilename, audioFilename);
                        LOGGER.info(exhibit.toString());

                        exhibitService.saveExhibit(qrCode, exhibit);

                        count++;
                    } else {
                    	Notification notification = new Notification(String.format("Failed to import exhibit %s with baseFilename %s", tailNumber, baseFilename));
                    	notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }
                }
                
                Notification notification = Notification.show(String.format("Finished importing %d exhibits from CSV", count));
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            } catch (NumberFormatException e) {
                LOGGER.warn("problem converting number while attempting to import exhibits from CSV file", e);
            } catch (IOException e) {
                LOGGER.error("problem with IO while attempting to import exhibits from CSV file", e);
            }
        });
        
        VerticalLayout vl = new VerticalLayout(label, upload);
        vl.setHorizontalComponentAlignment(Alignment.START, label, upload);
        vl.setMargin(true);
        
        return vl;
    }
    
    private VerticalLayout getQrCodeGenerationLayout() {
        Label label = new Label("Download the Zip file containing QR codes of all exhibits for printing.");
        
        sizeField.addValueChangeListener(l -> {
            String name = String.format("qrcodes-%s-%s.zip", sizeField.getValue(), fontSizeField.getValue());
			buttonWrapper.setResource(new StreamResource(name, getInputStreamFactory()));
        });
        
        fontSizeField.addValueChangeListener(l -> {
            String name = String.format("qrcodes-%s-%s.zip", sizeField.getValue(), fontSizeField.getValue());
			buttonWrapper.setResource(new StreamResource(name, getInputStreamFactory()));
        });
        
        sizeField.setLabel("Size (px)");
        sizeField.setValue(400);
        sizeField.setHasControls(true);
        sizeField.setMin(0);
        sizeField.setMax(1000);
        sizeField.setStep(50);

        fontSizeField.setLabel("Font Size (px)");
        fontSizeField.setValue(40);
        fontSizeField.setHasControls(true);
        fontSizeField.setMin(0);
        fontSizeField.setMax(100);
        fontSizeField.setStep(5);

        HorizontalLayout horizontalLayout = new HorizontalLayout(sizeField, fontSizeField);

		buttonWrapper.wrapComponent(new Button("Download Zip"));

        VerticalLayout vl = new VerticalLayout(label, horizontalLayout, buttonWrapper);
        vl.setHorizontalComponentAlignment(Alignment.START, label, horizontalLayout, buttonWrapper);
        vl.setMargin(true);
        
        return vl;
    }

	private InputStreamFactory getInputStreamFactory() {
		return () -> {
		    InputStream result = null;

			File workDir = new File(TMPDIR, "work");
			workDir.mkdir();
			
		    exhibitService.findAllExhibits().forEach(exhibit -> {
				String filename = String.format("%s-qrcode.png", FileUtils.getBase(exhibit.getImageFile()));
				QrCodeUtils.createAndWriteQR(exhibit.getTailNumber(), workDir, filename, sizeField.getValue(), fontSizeField.getValue());
			});
			
		    try {
				File zipFile = File.createTempFile("tmp-", ".zip", TMPDIR);
				zipFile.deleteOnExit();

				FileUtils.pack(workDir.getAbsolutePath(), zipFile);
				FileUtils.deleteDirectory(workDir);

				result = new FileInputStream(zipFile);
			} catch (IOException e) {
				LOGGER.error("error attempting to zip the QR codes", e);
			}

		    return result;
		};
	}
	
    
    private VerticalLayout getStatisticsLayout() {
        Label label = new Label("The usage statistics for various periods.");
        
        TableRow headerRow = table.addRow();
        headerRow.addHeaderCell().setText("");
        headerRow.addHeaderCell().setText("Android");
        headerRow.addHeaderCell().setText("iPhone");
        headerRow.addHeaderCell().setText("Windows Phone");
        headerRow.addHeaderCell().setText("Other");
        headerRow.addHeaderCell().setText("Total");

        table.setWidth("80%");
        
        VerticalLayout vl = new VerticalLayout(label, table);
        vl.setHorizontalComponentAlignment(Alignment.START, label, table);
        vl.setMargin(true);
        
        return vl;
    }
}
