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
public class VstoDate {

    @JsonProperty("date_id")
    @SerializedName("date_id")
    private int date_id;
    @JsonProperty("year")
    @SerializedName("year")
    private String year;
    @JsonProperty("month")
    @SerializedName("month")
    private String month;
    @JsonProperty("day")
    @SerializedName("day")
    private String day;

    public VstoDate(final int date_id, final String year, final String month, final String day) {
        this.date_id = date_id;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getDateId() {
        return this.date_id;
    }

    public String getYear() {
        return this.year;
    }

    public String getMonth() {
        return this.month;
    }

    public String getDay() {
        return this.day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VstoDate date = (VstoDate) o;
        if (getYear() != date.getYear()) return false;
        if (getMonth() != date.getMonth()) return false;
        if (getDay() != date.getDay()) return false;
        return true;
    }

    @Override
    public String toString() {
        return "VstoDate{" +
                "date_id=" + date_id +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                '}';
    }
}

