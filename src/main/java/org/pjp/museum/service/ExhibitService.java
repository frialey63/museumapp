package org.pjp.museum.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.pjp.museum.model.Exhibit;
import org.pjp.museum.repository.ExhibitRepository;
import org.pjp.museum.service.bean.TailNumber;
import org.pjp.museum.ui.util.QrCodeUtils;
import org.pjp.museum.util.UuidStr;
import org.springframework.stereotype.Service;

@Service
public class ExhibitService {

    public static final String MUSEUM_UUID = UuidStr.random();

    public static final String TEST_BUCCANEER_UUID = UuidStr.random();

    public static final String TEST_CANBERRA_UUID = UuidStr.random();

    public static final String TEST_HUNTER_UUID = UuidStr.random();

    private final ExhibitRepository repository;

    public ExhibitService(ExhibitRepository repository) {
        super();
        this.repository = repository;
    }

    public Optional<Exhibit> getExhibit(String id) {
        return repository.findById(id);
    }

    public List<TailNumber> getTailNumbers() {
        return repository.findAll().stream().filter(Exhibit::hasTailNumber).map(e -> new TailNumber(e.getTailNumber(), e.getUuid())).sorted().collect(Collectors.toList());
    }

    public String saveExhibit(String qrCode, Exhibit exhibit) {
        String uuid = UuidStr.random();

        if (QrCodeUtils.createAndWriteQR(uuid, qrCode)) {
            exhibit.setUuid(uuid);
            repository.save(exhibit);
        } else {
            uuid = null;
        }

        return uuid;
    }

    public void testData() {
        repository.deleteAll();

        {
            String description = "The RAF Manston History Museum started life as the RAF Manston History Club in 1986 with the intention of presenting exhibits and artefacts connected with this famous airfield from its beginning on 29th May 1916 as Royal Naval Air Service Manston. Housed in a large wooden hut that had been used by the Intelligence Section during World War 2, the museum has had a couple of moves to other buildings before settling in its present location, the former Mechanical Transport Section hangars, in the mid 1990's.";
            Exhibit exhibit = new Exhibit(MUSEUM_UUID, 0, "The RAF Manston History Museum", "", description, "museum.jpg", "museum.wav");
            repository.save(exhibit);
        }

        if (QrCodeUtils.createAndWriteQR(TEST_BUCCANEER_UUID, "buccaneer-qrcode.jpg")) {
            String description = "The Buccaneer was originally designed as a Maritime Strike aircraft for the Royal Navy, under the requirement designation NA.39. " +
                    "Later adopted by the Royal Air Force, the Buccaneer had a successful career, culminating with participation in the Gulf War.";
            Exhibit exhibit = new Exhibit(TEST_BUCCANEER_UUID, 0, "Blackburn Buccaneer S.2B", "XV352", description, "buccaneer.jpg", "buccaneer.wav");
            repository.save(exhibit);
        }

        if (QrCodeUtils.createAndWriteQR(TEST_CANBERRA_UUID, "canberra-qrcode.jpg")) {
            String description = "English Electric Canberra WT205 was built in 1955 as part of Contract 6/ACFT/6448 as a B6 bomber, by Short Bros & Harland in Belfast.  Fifty aircraft were built in this batch.";
            Exhibit exhibit = new Exhibit(TEST_CANBERRA_UUID, 0, "English Electric Canberra B6", "WT205", description, "canberra.jpg", "canberra.wav");
            repository.save(exhibit);
        }

        if (QrCodeUtils.createAndWriteQR(TEST_HUNTER_UUID, "hunter-qrcode.jpg")) {
            String description = "XG226 was part of a third production batch of 110 F.Mk.6A Hunters constructed by Hawker Siddeley at Kingston-upon-Thames, and first took to the air on 28th September 1956, piloted by Hawker test pilot Frank Bullen.";
            Exhibit exhibit = new Exhibit(TEST_HUNTER_UUID, 0, "Hawker Siddeley Hunter F.Mk.6A ", "XG226", description, "hunter.jpg", "hunter.wav");
            repository.save(exhibit);
        }
    }
}
