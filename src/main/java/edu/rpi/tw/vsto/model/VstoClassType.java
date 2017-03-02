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
public class VstoClassType {

    @JsonProperty("id")
    @SerializedName("id")
    private int id;
    @JsonProperty("name")
    @SerializedName("name")
    private String name;
    private int parentId;
    @JsonProperty("parent")
    @SerializedName("parent")
    private VstoClassType parent;
    private int noteId;
    @JsonProperty("note")
    @SerializedName("note")
    private Note note;

    public VstoClassType(final int id, final String name, final int parentId, final int noteId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.noteId = noteId;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getParentId() {
        return this.parentId;
    }

    public VstoClassType getParent() {
        return this.parent;
    }

    public void setParent(final VstoClassType parent) {
        this.parent = parent;
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

        VstoClassType classType = (VstoClassType) o;
        return this.id == classType.getId();
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parent='" + parent + '\'' +
                ", noteId='" + noteId + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}

