package io.sfinias.toggl.model;

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
    private long id;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("workspace_id")
    private int workspaceId;

    @JsonProperty("tag_ids")
    @Nullable
    private List<Integer> tagIds;

    @JsonProperty("start")
    private Instant start;

    @JsonProperty("stop")
    @Nullable
    private Instant stop;

    @JsonProperty("billable")
    private boolean billable;

    @JsonProperty("task_id")
    @Nullable
    private Integer taskId;

    @JsonProperty("tags")
    private List<String> tags = new ArrayList<>();

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("at")
    private String at;

    @JsonProperty("project_id")
    private int projectId;

    @JsonProperty("duronly")
    @Nullable
    private boolean duronly;

    @JsonProperty("server_deleted_at")
    @Nullable
    private Instant serverDeletedAt;

    public long getId() {

        return id;
    }

    public int getUserId() {

        return userId;
    }

    public String getDescription() {

        return description;
    }

    public int getWorkspaceId() {

        return workspaceId;
    }

    public List<Integer> getTagIds() {

        return tagIds;
    }

    public Instant getStart() {

        return start;
    }

    public Instant getStop() {

        return stop;
    }

    public boolean isBillable() {

        return billable;
    }

    public Integer getTaskId() {

        return taskId;
    }

    public List<String> getTags() {

        return tags;
    }

    public int getDuration() {

        return duration;
    }

    public String getAt() {

        return at;
    }

    public int getProjectId() {

        return projectId;
    }

    public boolean isDuronly() {

        return duronly;
    }

    public Instant getServerDeletedAt() {

        return serverDeletedAt;
    }
}