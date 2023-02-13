package io.sfinias.toggl.dto;

import java.time.ZonedDateTime;

public class TogglTimeEntry {

    private String description = "";

    private String projectName = "";

    private ZonedDateTime start = null;

    private ZonedDateTime end = null;

    public TogglTimeEntry() {

    }

    public TogglTimeEntry(String description, String projectName, ZonedDateTime start, ZonedDateTime end) {

        this.description = description;
        this.projectName = projectName;
        this.start = start;
        this.end = end;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getProjectName() {

        return projectName;
    }

    public void setProjectName(String projectName) {

        this.projectName = projectName;
    }

    public ZonedDateTime getStart() {

        return start;
    }

    public void setStart(ZonedDateTime start) {

        this.start = start;
    }

    public ZonedDateTime getEnd() {

        return end;
    }

    public void setEnd(ZonedDateTime end) {

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
