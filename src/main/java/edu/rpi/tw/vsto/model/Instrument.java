package edu.rpi.tw.vsto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pwest on 12/09/16.
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Instrument {

    @JsonProperty("kinst")
    @SerializedName("kinst")
    private int kinst;
    @JsonProperty("name")
    @SerializedName("name")
    private String name;
    @JsonProperty("prefix")
    @SerializedName("prefix")
    private String prefix;
    @JsonProperty("description")
    @SerializedName("description")
    private String description;
    private int classTypeId;
    @JsonProperty("class_type")
    @SerializedName("class_type")
    private VstoClassType classType;
    private int observatoryId;
    @JsonProperty("observatory")
    @SerializedName("observatory")
    private Observatory observatory;
    private int opModeId;
    @JsonProperty("op_mode")
    @SerializedName("op_mode")
    private List<OpMode> opModes;
    private int noteId;
    @JsonProperty("note")
    @SerializedName("note")
    private Note note;

    public Instrument(final int kinst, final String name, final String prefix, final String description,
                      final int classTypeId, final int observatoryId, final int opModeId, final int noteId) {
        this.kinst = kinst;
        this.name = name;
        this.prefix = prefix;
        this.description = description;
        this.classTypeId = classTypeId;
        this.observatoryId = observatoryId;
        this.opModeId = opModeId;
        this.noteId = noteId;
    }

    public int getKinst() {
        return this.kinst;
    }

    public void setKinst(final int kinst) {
        this.kinst = kinst;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getClassTypeId() {
        return this.classTypeId;
    }

    public VstoClassType getClassType() {
        return this.classType;
    }

    public void setClassType(final VstoClassType classType) {
        this.classType = classType;
    }

    public int getObservatoryId() {
        return this.observatoryId;
    }

    public Observatory getObservatory() {
        return this.observatory;
    }

    public void setObservatory(final Observatory observatory) {
        this.observatory = observatory;
    }

    public int getOpModeId() {
        return this.opModeId;
    }

    public List<OpMode> getOpModes() {
        return this.opModes;
    }

    public void setOpModes(final List<OpMode> opModes) {
        this.opModes = opModes;
    }

    public int getNoteId() {
        return noteId;
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

        Instrument instrument = (Instrument) o;
        return this.kinst == instrument.getKinst();
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "kinst=" + kinst +
                ", name='" + name + '\'' +
                ", prefix='" + prefix + '\'' +
                ", description='" + description + '\'' +
                ", classType='" + classType + '\'' +
                ", observatory='" + observatory + '\'' +
                ", opMode='" + opModes + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}

