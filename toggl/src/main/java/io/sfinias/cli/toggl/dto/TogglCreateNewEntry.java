package io.sfinias.cli.toggl.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class TogglCreateNewEntry {

    @NotBlank(message = "Description cannot be empty")
    @Min(value = 3, message = "Description must be longer than 3 characters")
    public final String description;

    @NotBlank(message = "Project name cannot be empty")
    @Min(value = 3, message = "Project name must be longer than 3 characters")
    public final String project;

    @NotNull(message = "Must provide a start timestamp")
    public final LocalDateTime start;

    @NotNull(message = "Must provide an end timestamp")
    public final LocalDateTime end;

    public final String apiKey;

    public TogglCreateNewEntry(String description, String project, LocalDateTime start, LocalDateTime end, String apiKey) {

        this.description = description;
        this.project = project;
        this.start = start;
        this.end = end;
        this.apiKey = apiKey;
    }
}
