package com.sfinias.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTimeEntryModel {

    @JsonProperty("time_entry")
    private TimeEntryModel timeEntry;

    public TimeEntryModel getTimeEntry() {

        return timeEntry;
    }

    public void setTimeEntry(TimeEntryModel timeEntry) {

        this.timeEntry = timeEntry;
    }

    @Override
    public String toString() {

        return "RequestTimeEntryModel{" +
                "timeEntry=" + timeEntry +
                '}';
    }
}
