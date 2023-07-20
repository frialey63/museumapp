package org.pjp.museum.ui.view.importcsv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.pjp.museum.model.Exhibit;
import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.ui.util.AudioUtils;
import org.pjp.museum.ui.util.FileUtils;
import org.pjp.museum.ui.util.ImageUtils;
import org.pjp.museum.ui.util.QrCodeUtils;
import org.pjp.museum.ui.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.olli.FileDownloadWrapper;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;

@PageTitle("Import CSV")
public class ImportCsvView extends VerticalLayout {

    private static final long serialVersionUID = 3386437553156944523L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportCsvView.class);

	private static final File TMPDIR = new File(System.getProperty("java.io.tmpdir"));
	
    private static final String TEXT_EXTN = ".txt";

    private static final String IMAGE_EXTN = ".jpg";

    private static final String AUDIO_EXTN = ".wav";

    private static final String QR_EXTN = "-qrcode.png";

    private MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();

    private final FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(null);

    /**
     * @param service
     */
    public ImportCsvView(ExhibitService service) {
        super();
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

                        service.saveExhibit(qrCode, exhibit);

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

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, label, upload);

        add(label, upload);
        
        int size = 400;
        int fontSize = 40;
        
		String downloadFilename = String.format("qrcodes-%d.zip", size);

        label = new Label("Download the Zip file containing QR codes of all exhibits for printing.");

		buttonWrapper.wrapComponent(new Button("Download Zip"));
        buttonWrapper.setResource(new StreamResource(downloadFilename, () -> {
            InputStream result = null;

    		LOGGER.info("tmpdir = {}", TMPDIR);
    		
    		File workDir = new File(TMPDIR, "work");
    		workDir.mkdir();
    		
    		service.findAllExhibits().forEach(exhibit -> {
    			String filename = String.format("%s-qrcode-%d.png", FileUtils.getBase(exhibit.getImageFile()), size);
        		QrCodeUtils.createAndWriteQR(exhibit.getTailNumber(), workDir, filename, size, fontSize);
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
        }));

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, label, buttonWrapper);

        add(label, buttonWrapper);
    }

}
