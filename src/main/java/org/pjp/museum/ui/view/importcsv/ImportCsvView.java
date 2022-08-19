package org.pjp.museum.ui.view.importcsv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.pjp.museum.model.Exhibit;
import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.ui.view.MainLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Import CSV")
@Route(value = "importcsv", layout = MainLayout.class)
public class ImportCsvView extends VerticalLayout {

    private static final long serialVersionUID = 3386437553156944523L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportCsvView.class);

    private MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();

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
                    String qrCode = record.get("qrCode");
                    int displayOrder = Integer.parseInt(record.get("displayOrder"));
                    String name = record.get("name");
                    String tailNumber = record.get("tailNumber");
                    String description = record.get("description");
                    String imageFile = record.get("imageFile");
                    String audioFile = record.get("audioFile");

                    Exhibit exhibit = new Exhibit(null, displayOrder, name, tailNumber, description, imageFile, audioFile);
                    LOGGER.info(exhibit.toString());

                    service.saveExhibit(qrCode, exhibit);

                    count++;
                }

                LOGGER.info("finished loading {} exhibits from CSV", count);
            } catch (NumberFormatException e) {
                LOGGER.warn("problem converting number while attempting to import exhibits from CSV file", e);
            } catch (IOException e) {
                LOGGER.error("problem with IO while attempting to import exhibits from CSV file", e);
            }
        });

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, label, upload);

        add(label, upload);
    }

}
