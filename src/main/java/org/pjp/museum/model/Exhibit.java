package org.pjp.museum.model;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Exhibit {

    @Id
    private String uuid;

    private int displayOrder;

    @NotBlank
    private String name;

    @NotNull
    private String tailNumber;

    @NotBlank
    private String description;

    @NotBlank
    private String imageFile;

    @NotBlank
    private String audioFile;

    public Exhibit() {
        super();
    }

    public Exhibit(String uuid, int displayOrder, @NotBlank String name, @NotBlank String tailNumber, @NotBlank String description, @NotBlank String imageFile, @NotBlank String audioFile) {
        super();
        this.uuid = uuid;
        this.displayOrder = displayOrder;
        this.name = name;
        this.tailNumber = tailNumber;
        this.description = description;
        this.imageFile = imageFile;
        this.audioFile = audioFile;
    }

    public Exhibit(int displayOrder, @NotBlank String name, @NotBlank String tailNumber, @NotBlank String description, @NotBlank String imageFile, @NotBlank String audioFile) {
        this(null, displayOrder, name, tailNumber, description, imageFile, audioFile);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public boolean hasTailNumber() {
        return !tailNumber.isBlank();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Exhibit other = (Exhibit) obj;
        return Objects.equals(uuid, other.uuid);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Exhibit [uuid=");
        builder.append(uuid);
        builder.append(", displayOrder=");
        builder.append(displayOrder);
        builder.append(", name=");
        builder.append(name);
        builder.append(", tailNumber=");
        builder.append(tailNumber);
        builder.append(", description=");
        builder.append(description);
        builder.append(", imageFile=");
        builder.append(imageFile);
        builder.append(", audioFile=");
        builder.append(audioFile);
        builder.append("]");
        return builder.toString();
    }

}
