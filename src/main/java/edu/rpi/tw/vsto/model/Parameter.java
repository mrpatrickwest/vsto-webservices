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
public class Parameter {

    @JsonProperty("id")
    @SerializedName("id")
    private int id;
    @JsonProperty("short_name")
    @SerializedName("short_name")
    private String shortName;
    @JsonProperty("long_name")
    @SerializedName("long_name")
    private String longName;
    @JsonProperty("madrigal_name")
    @SerializedName("madrigal_name")
    private String madrigalName;
    @JsonProperty("units")
    @SerializedName("units")
    private String units;
    @JsonProperty("scale")
    @SerializedName("scale")
    private String scale;
    private int noteId;
    @JsonProperty("note")
    @SerializedName("note")
    private Note note;

    public Parameter(final int id, final String shortName, final String longName, final String madrigalName,
                     final String units, final String scale, final int noteId) {
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
        this.madrigalName = madrigalName;
        this.units = units;
        this.scale = scale;
        this.noteId = noteId;
    }

    public int getId() {
        return this.id;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getLongName() {
        return this.longName;
    }

    public String getMadrigalName() {
        return this.madrigalName;
    }

    public String getUnits() {
        return this.units;
    }

    public String getScale() {
        return this.scale;
    }

    public int getNoteId() {
        return this.noteId;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(final Note note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;
        return this.id == parameter.getId();
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                ", madrigalName='" + madrigalName + '\'' +
                ", units='" + units + '\'' +
                ", scale='" + scale + '\'' +
                ", noteId='" + noteId + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}

