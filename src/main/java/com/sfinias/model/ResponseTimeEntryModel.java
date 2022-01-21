package com.sfinias.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseTimeEntryModel {

    @JsonProperty("data")
    private TimeEntryModel data;

    public TimeEntryModel getData() {

        return data;
    }

    public void setData(TimeEntryModel data) {

        this.data = data;
    }

    @Override
    public String toString() {

        return "ResponseTimeEntryModel{" +
                "data=" + data +
                '}';
    }
}
