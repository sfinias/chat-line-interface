package io.sfinias.cli.toggl.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

@JsonInclude(Include.NON_NULL)
public class RequestTimeEntryModel {

    @JsonProperty("user_id")
    //If omitted defaults to the requester
    public Integer userId;

    @JsonProperty("description")
    public String description;

    @JsonProperty("created_with")
    public String createdWith;

    @JsonProperty("project_id")
    public Integer projectId;

    @JsonProperty("workspace_id")
    public int workspaceId;

    @JsonProperty("start")
    public Instant start;

    @JsonProperty("stop")
    public Instant stop;

    @JsonProperty("billable")
    public boolean billable;

    @JsonProperty("task_id")
    public Integer taskId;

    @JsonProperty("duration")
    public Integer duration;
}
