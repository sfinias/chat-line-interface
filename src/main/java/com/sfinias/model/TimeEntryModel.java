package com.sfinias.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class TimeEntryModel {

    @JsonProperty("duration")
    private long duration;

    @JsonProperty("wid")
    private Long wid;

    @JsonProperty("at")
    private Instant at;

    @JsonProperty("stop")
    private Instant stop;

    @JsonProperty("start")
    private Instant start;

    @JsonProperty("guid")
    private String guid;

    @JsonProperty("description")
    private String description;

    @JsonProperty("pid")
    private Long pid;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("billable")
    private Boolean billable;

    @JsonProperty("duronly")
    private Boolean duronly;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("created_with")
    private String createdWith;

    public Long getDuration() {

        return duration;
    }

    public Long getWid() {

        return wid;
    }

    public Instant getAt() {

        return at;
    }

    public Instant getStop() {

        return stop;
    }

    public Instant getStart() {

        return start;
    }

    public String getGuid() {

        return guid;
    }

    public String getDescription() {

        return description;
    }

    public Long getPid() {

        return pid;
    }

    public Long getId() {

        return id;
    }

    public Boolean isBillable() {

        return billable;
    }

    public Boolean isDuronly() {

        return duronly;
    }

    public List<String> getTags() {

        return tags;
    }

    public void setDuration(Long duration) {

        this.duration = duration;
    }

    public void setWid(Long wid) {

        this.wid = wid;
    }

    public void setAt(Instant at) {

        this.at = at;
    }

    public void setStop(Instant stop) {

        this.stop = stop;
    }

    public void setStart(Instant start) {

        this.start = start;
    }

    public void setGuid(String guid) {

        this.guid = guid;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setPid(Long pid) {

        this.pid = pid;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public void setBillable(Boolean billable) {

        this.billable = billable;
    }

    public void setDuronly(Boolean duronly) {

        this.duronly = duronly;
    }

    public void setTags(List<String> tags) {

        this.tags = tags;
    }

    public String getCreatedWith() {

        return createdWith;
    }

    public void setCreatedWith(String createdWith) {

        this.createdWith = createdWith;
    }

    @Override
    public String toString() {

        return "TimeEntryModel{" +
                "duration=" + duration +
                ", wid=" + wid +
                ", at=" + at +
                ", stop=" + stop +
                ", start=" + start +
                ", guid='" + guid + '\'' +
                ", description='" + description + '\'' +
                ", pid=" + pid +
                ", id=" + id +
                ", billable=" + billable +
                ", duronly=" + duronly +
                ", tags=" + tags +
                ", createdWith='" + createdWith + '\'' +
                '}';
    }
}