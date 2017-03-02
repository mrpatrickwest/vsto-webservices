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
public class OpMode {

    @JsonProperty("id")
    @SerializedName("id")
    private int id;
    @JsonProperty("kindat")
    @SerializedName("kindat")
    private int kindat;
    @JsonProperty("kinst")
    @SerializedName("kinst")
    private int kinst;
    @JsonProperty("description")
    @SerializedName("description")
    private String description;

    public OpMode(final int id, final int kindat, final int kinst, final String description) {
        this.id = id;
        this.kindat = kindat;
        this.kinst = kinst;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public int getKindat() {
        return this.kindat;
    }

    public int getKinst() {
        return this.kinst;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpMode opMode = (OpMode) o;
        return this.kinst == opMode.getKinst() && this.kindat == opMode.getKindat();
    }

    @Override
    public String toString() {
        return "OpMode{" +
                "id=" + id +
                "kinst=" + kinst +
                "kindat=" + kindat +
                ", description='" + description + '\'' +
                '}';
    }
}

