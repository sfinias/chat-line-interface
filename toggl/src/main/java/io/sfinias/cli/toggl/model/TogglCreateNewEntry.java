package io.sfinias.cli.toggl.model;

import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TogglCreateNewEntry {

    @NotBlank(message = "Description cannot be empty")
    @Min(value = 3, message = "Description must be longer than 3 characters")
    private String description;

    @NotBlank(message = "Project name cannot be empty")
    @Min(value = 3, message = "Project name must be longer than 3 characters")
    private String project;

    @NotNull(message = "Must provide a start timestamp")
    private LocalDateTime start;

    @NotNull(message = "Must provide an end timestamp")
    private LocalDateTime end;

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getProject() {

        return project;
    }

    public void setProject(String project) {

        this.project = project;
    }

    public LocalDateTime getStart() {

        return start;
    }

    public void setStart(LocalDateTime start) {

        this.start = start;
    }

    public LocalDateTime getEnd() {

        return end;
    }

    public void setEnd(LocalDateTime end) {

        this.end = end;
    }
}
