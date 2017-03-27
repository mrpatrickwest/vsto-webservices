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
public class CedarFile {

    @JsonProperty("file_id")
    @SerializedName("file_id")
    private int file_id;
    @JsonProperty("file_name")
    @SerializedName("file_name")
    private String fileName;
    @JsonProperty("file_size")
    @SerializedName("file_size")
    private int fileSize;
    @JsonProperty("file_mark")
    @SerializedName("file_mark")
    private String fileMark;
    @JsonProperty("nrecords")
    @SerializedName("nrecords")
    private int nRecords;

    public CedarFile(final int file_id, final String fileName, final int fileSize, final String fileMark, final int nRecords) {
        this.file_id = file_id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileMark = fileMark;
        this.nRecords = nRecords;
    }

    public int getFileId() {
        return this.file_id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public String getFileMark() {
        return this.fileMark;
    }

    public int getNRecords() {
        return this.nRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CedarFile date = (CedarFile) o;
        if (getFileName() != date.getFileName()) return false;
        if (getFileSize() != date.getFileSize()) return false;
        if (getNRecords() != date.getNRecords()) return false;
        return true;
    }

    @Override
    public String toString() {
        return "CedarFile{" +
                "file_id=" + file_id +
                ", fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", fileMark='" + fileMark + '\'' +
                ", nRecords='" + nRecords + '\'' +
                '}';
    }
}

