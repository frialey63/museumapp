package org.pjp.museum.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Setting {

    public static final String CLOSING_TIME = "CLOSING_TIME";

    @Id
    private String uuid;

    @NotBlank
    private String name;

    @NotNull
    private Object value;

    public Setting(String uuid, @NotBlank String name, @NotNull Object value) {
        super();
        this.uuid = uuid;
        this.name = name;
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Setting [uuid=");
        builder.append(uuid);
        builder.append(", name=");
        builder.append(name);
        builder.append(", value=");
        builder.append(value);
        builder.append("]");
        return builder.toString();
    }

}