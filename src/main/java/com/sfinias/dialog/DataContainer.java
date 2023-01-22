package com.sfinias.dialog;

import com.sfinias.model.ProjectModel;
import com.sfinias.model.TimeEntryModel;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.telegram.telegrambots.meta.api.objects.User;

public class DataContainer {

    final User user;

    DialogIntent intent;
    String apiKey;
    List<TimeEntryModel> oldEntries;
    LocalDate selectedDate;
    Map<Integer, ProjectModel> projects;
    ProjectModel selectedProject;
    String description;

    public DataContainer(User user) {

        this.user = user;
    }

    public DialogIntent getIntent() {

        return intent;
    }

    public void setIntent(DialogIntent intent) {

        this.intent = intent;
    }

    public User getUser() {

        return user;
    }

    public String getApiKey() {

        return apiKey;
    }

    public void setApiKey(String apiKey) {

        this.apiKey = apiKey;
    }

    public List<TimeEntryModel> getOldEntries() {

        return oldEntries;
    }

    public void setOldEntries(List<TimeEntryModel> oldEntries) {

        this.oldEntries = oldEntries;
    }

    public LocalDate getSelectedDate() {

        return selectedDate;
    }

    public void setSelectedDate(LocalDate selectedDate) {

        this.selectedDate = selectedDate;
    }

    public Map<Integer, ProjectModel> getProjects() {

        return projects;
    }

    public void setProjects(Map<Integer, ProjectModel> projects) {

        this.projects = projects;
    }

    public ProjectModel getSelectedProject() {

        return selectedProject;
    }

    public void setSelectedProject(ProjectModel selectedProject) {

        this.selectedProject = selectedProject;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }
}
