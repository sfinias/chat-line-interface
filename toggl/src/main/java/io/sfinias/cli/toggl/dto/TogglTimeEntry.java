package io.sfinias.cli.toggl.dto;

import java.time.ZonedDateTime;

public class TogglTimeEntry {

    public final String description;

    public final String projectName;

    public final ZonedDateTime start;

    public final ZonedDateTime end;

    public TogglTimeEntry(String description, String projectName, ZonedDateTime start, ZonedDateTime end) {

        this.description = description;
        this.projectName = projectName;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {

        return "TogglTimeEntry{\n" +
                "\t\t\tdescription='" + description + "'\n" +
                "\t\t\tprojectName='" + projectName + "'\n" +
                "\t\t\tstart=" + start + '\n' +
                "\t\t\tend=" + end + '\n' +
                '}';
    }
}
