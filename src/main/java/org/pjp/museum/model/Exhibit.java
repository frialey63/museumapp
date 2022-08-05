package org.pjp.museum.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Exhibit {

    @Id
    private String uuid;

    @NotBlank
    private String name;

    @NotBlank
    private String tailNumber;

    @NotBlank
    private String imageFile;

    @NotBlank
    private String audioFile;

    public Exhibit() {
        super();
    }


}
