package org.pjp.museum.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.pjp.museum.model.Exhibit;
import org.pjp.museum.repository.ExhibitRepository;
import org.pjp.museum.service.ExhibitServiceTest.ExhibitServiceTestContextConfiguration;
import org.pjp.museum.service.bean.TailNumber;
import org.pjp.museum.ui.util.QrCodeUtils;
import org.pjp.museum.util.UuidStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(ExhibitServiceTestContextConfiguration.class)
class ExhibitServiceTest {

    @TestConfiguration
    static class ExhibitServiceTestContextConfiguration {

        @Autowired
        private ExhibitRepository repository;

        @Bean
        public ExhibitService exhibitService() {
            return new ExhibitService(repository);
        }
    }

    @Autowired
    private ExhibitService service;

    @MockBean
    private ExhibitRepository repository;

    @Test
    void testGetExhibit() {
        // GIVEN

        String tailNumber = "XV352";
        Exhibit exhibit = new Exhibit(UuidStr.random(), 0, "name", tailNumber, "description", "imageFile", "audioFile");

        // WHEN

        Mockito.when(repository.findByTailNumber(tailNumber)).thenReturn(Optional.of(exhibit));

        // THEN

        Optional<Exhibit> optExhibit = service.getExhibit(tailNumber);

        assertTrue(optExhibit.isPresent());
        assertEquals(exhibit, optExhibit.get());
    }

    @Test
    void testGetTailNumbers() {
        // GIVEN

        Exhibit exhibit1 = new Exhibit(UuidStr.random(), 0, "name1", "tailNumber1", "description1", "imageFile1", "audioFile1");
        Exhibit exhibit2 = new Exhibit(UuidStr.random(), 0, "name2", "tailNumber2", "description2", "imageFile2", "audioFile2");
        Exhibit exhibit3 = new Exhibit(UuidStr.random(), 0, "name3", "tailNumber3", "description3", "imageFile3", "audioFile3");

        // WHEN

        Mockito.when(repository.findAll()).thenReturn(List.of(exhibit1, exhibit2, exhibit3));

        // THEN

        List<TailNumber> tailNumbers = service.getTailNumbers();

        assertThat(tailNumbers, hasSize(3));
        assertThat(tailNumbers, containsInAnyOrder(
                new TailNumber(exhibit1.getTailNumber(), exhibit1.getUuid()),
                new TailNumber(exhibit2.getTailNumber(), exhibit2.getUuid()),
                new TailNumber(exhibit3.getTailNumber(), exhibit3.getUuid())));
    }

    @Test
    void testSaveExhibit() {
        // GIVEN

        File qrCodeFile = new File(QrCodeUtils.QRCODE_DIR, "exhibit.jpg");
        qrCodeFile.deleteOnExit();

        String uuid = UuidStr.random();
        Exhibit exhibit = new Exhibit(uuid, 0, "name", "tailNumber", "description", "imageFile", "audioFile");

        // WHEN

        service.saveExhibit(qrCodeFile.getName(), exhibit);

        // THEN

        assertTrue(qrCodeFile.exists());

        Mockito.verify(repository).save(exhibit);
    }

}
