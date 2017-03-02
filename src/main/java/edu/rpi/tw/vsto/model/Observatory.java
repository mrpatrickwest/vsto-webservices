package edu.rpi.tw.vsto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pwest on 12/09/16.
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Observatory {

    @JsonProperty("id")
    @SerializedName("id")
    private int id;
    @JsonProperty("code")
    @SerializedName("code")
    private String code;
    @JsonProperty("long_name")
    @SerializedName("long_name")
    private String longName;
    @JsonProperty("description")
    @SerializedName("description")
    private String description;
    @JsonProperty("duty_cycle")
    @SerializedName("duty_cycle")
    private String dutyCycle;
    @JsonProperty("hours")
    @SerializedName("hours")
    private String hours;
    @JsonProperty("url")
    @SerializedName("url")
    private String url;
    private int noteId;
    @JsonProperty("note")
    @SerializedName("note")
    private Note note;

    public Observatory(final int id, final String code, final String longName, final String description,
                       final String dutyCycle, final String hours, final String url, final int noteId) {
        this.id = id;
        this.code = code;
        this.longName = longName;
        this.description = description;
        this.dutyCycle = dutyCycle;
        this.hours = hours;
        this.url = url;
        this.noteId = noteId;
    }

    public int getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getLongName() {
        return this.longName;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDutyCycle() {
        return this.dutyCycle;
    }

    public String getHours() {
        return this.hours;
    }

    public String getURL() {
        return this.url;
    }

    public int getNoteId() {
        return this.noteId;
    }

    public Note getNote() {
        return this.note;
    }

    public void setNote(final Note note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Observatory observatory = (Observatory) o;
        return this.id == observatory.getId();
    }

    @Override
    public String toString() {
        return "Observatory{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", longName='" + longName + '\'' +
                ", description='" + description + '\'' +
                ", dutyCycle='" + dutyCycle + '\'' +
                ", hours='" + hours + '\'' +
                ", url='" + url + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}

