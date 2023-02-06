package com.sfinias.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

@JsonInclude(Include.NON_NULL)
public class RequestTimeEntryModel {

    @JsonProperty("user_id")
    //If omitted defaults to the requester
    private Integer userId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("created_with")
    private String createdWith;

    @JsonProperty("project_id")
    private Integer projectId;

    @JsonProperty("workspace_id")
    private int workspaceId;

    @JsonProperty("start")
    private Instant start;

    @JsonProperty("stop")
    private Instant stop;

    @JsonProperty("billable")
    private boolean billable;

    @JsonProperty("task_id")
    private Integer taskId;

    @JsonProperty("duration")
    private Integer duration;

    public Integer getUserId() {

        return userId;
    }

    public void setUserId(Integer userId) {

        this.userId = userId;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getCreatedWith() {

        return createdWith;
    }

    public void setCreatedWith(String createdWith) {

        this.createdWith = createdWith;
    }

    public Integer getProjectId() {

        return projectId;
    }

    public void setProjectId(Integer projectId) {

        this.projectId = projectId;
    }

    public int getWorkspaceId() {

        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {

        this.workspaceId = workspaceId;
    }

    public Instant getStart() {

        return start;
    }

    public void setStart(Instant start) {

        this.start = start;
    }

    public Instant getStop() {

        return stop;
    }

    public void setStop(Instant stop) {

        this.stop = stop;
    }

    public boolean isBillable() {

        return billable;
    }

    public void setBillable(boolean billable) {

        this.billable = billable;
    }

    public Integer getTaskId() {

        return taskId;
    }

    public void setTaskId(Integer taskId) {

        this.taskId = taskId;
    }

    public Integer getDuration() {

        return duration;
    }

    public void setDuration(Integer duration) {

        this.duration = duration;
    }
}
