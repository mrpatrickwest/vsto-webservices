package edu.rpi.tw.vsto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * Created by pwest on 12/09/16.
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Note {

    @JsonProperty("id")
    @SerializedName("id")
    private int id;
    @JsonProperty("user")
    @SerializedName("user")
    private String user;
    @JsonProperty("description")
    @SerializedName("description")
    private String description;
    @JsonProperty("date")
    @SerializedName("date")
    private String date;
    private int nextNoteId;
    @JsonProperty("next")
    @SerializedName("next")
    private Note nextNote;
    @JsonProperty("public")
    @SerializedName("public")
    private boolean isPublic;

    public Note(final int id, final String user, final String description, final Timestamp date,
                final int nextNoteId, final boolean isPublic) {
        this.id = id;
        this.user = user;
        this.description = description;
        if(date != null) this.date = date.toString();
        else this.date = null;
        this.nextNoteId = nextNoteId;
        this.isPublic = isPublic;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
    }

    public int getNextNoteId() {
        return this.nextNoteId;
    }

    public Note getNextNote() {
        return this.nextNote;
    }

    public void setNextNote(final Note nextNote) {
        this.nextNote = nextNote;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;
        return this.id == note.getId();
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", nextNoteId='" + nextNoteId + '\'' +
                ", nextNote='" + nextNote + '\'' +
                ", isPublic='" + isPublic + '\'' +
                '}';
    }
}

