package org.pjp.museum.service;

import java.util.Optional;
import java.util.UUID;

import org.pjp.museum.model.Exhibit;
import org.pjp.museum.repository.ExhibitRepository;
import org.pjp.museum.ui.util.QrCodeUtils;
import org.springframework.stereotype.Service;

@Service
public class ExhibitService {

    public static final String TEST_BUCCANEER_UUID = UUID.randomUUID().toString();

    public static final String TEST_CANBERRA_UUID = UUID.randomUUID().toString();

    public static final String TEST_HUNTER_UUID = UUID.randomUUID().toString();

    private final ExhibitRepository repository;

    public ExhibitService(ExhibitRepository repository) {
        super();
        this.repository = repository;
    }

    public Optional<Exhibit> getExhibit(String id) {
        return repository.findById(id);
    }

    public void testData() {
        repository.deleteAll();

        if (QrCodeUtils.createAndWriteQR(TEST_BUCCANEER_UUID, "buccaneer.png")) {
            String description = "The Buccaneer was originally designed as a Maritime Strike aircraft for the Royal Navy, under the requirement designation NA.39.<br/>" +
                    "Later adopted by the Royal Air Force, the Buccaneer had a successful career, culminating with participation in the Gulf War.";
            Exhibit exhibit = new Exhibit(TEST_BUCCANEER_UUID, "Blackburn Buccaneer S.2B", "XV352", description, "buccaneer.jpg", "buccaneer.wav");
            repository.save(exhibit);
        }

        if (QrCodeUtils.createAndWriteQR(TEST_CANBERRA_UUID, "canberra.png")) {
            String description = "English Electric Canberra WT205 was built in 1955 as part of Contract 6/ACFT/6448 as a B6 bomber, by Short Bros & Harland in Belfast.  Fifty aircraft were built in this batch.";
            Exhibit exhibit = new Exhibit(TEST_CANBERRA_UUID, "English Electric Canberra B6", "WT205", description, "canberra.jpg", "canberra.wav");
            repository.save(exhibit);
        }

        if (QrCodeUtils.createAndWriteQR(TEST_HUNTER_UUID, "hunter.png")) {
            String description = "XG226 was part of a third production batch of 110 F.Mk.6A Hunters constructed by Hawker Siddeley at Kingston-upon-Thames, and first took to the air on 28th September 1956, piloted by Hawker test pilot Frank Bullen.";
            Exhibit exhibit = new Exhibit(TEST_HUNTER_UUID, "Hawker Siddeley Hunter F.Mk.6A ", "XG226", description, "hunter.jpg", "hunter.wav");
            repository.save(exhibit);
        }
    }
}
