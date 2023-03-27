package io.sfinias.cli.toggl.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class TogglTimeEntryModel {

    @JsonProperty("id")
    public long id;

    @JsonProperty("user_id")
    public int userId;

    @JsonProperty("description")
    public String description;

    @JsonProperty("workspace_id")
    public int workspaceId;

    @JsonProperty("tag_ids")
    @Nullable
    public List<Integer> tagIds;

    @JsonProperty("start")
    public Instant start;

    @JsonProperty("stop")
    @Nullable
    public Instant stop;

    @JsonProperty("billable")
    public boolean billable;

    @JsonProperty("task_id")
    @Nullable
    public Integer taskId;

    @JsonProperty("tags")
    public List<String> tags = new ArrayList<>();

    @JsonProperty("duration")
    public int duration;

    @JsonProperty("at")
    public String at;

    @JsonProperty("project_id")
    public int projectId;

    @JsonProperty("duronly")
    @Nullable
    public boolean duronly;

    @JsonProperty("server_deleted_at")
    @Nullable
    public Instant serverDeletedAt;
}