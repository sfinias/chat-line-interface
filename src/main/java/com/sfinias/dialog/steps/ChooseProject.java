package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.model.ProjectModel;
import java.util.stream.Collectors;

public class ChooseProject extends ListClientRequestStep{

    public ChooseProject() {

        super("Choose a project", container -> container.getProjects().entrySet().stream()
                .collect(Collectors.toMap(entry -> String.valueOf(entry.getKey()), entry -> entry.getValue().getName())));
    }

    @Override
    public boolean isApplicable(DataContainer container) {

        return container.getSelectedProject() == null;
    }

    @Override
    public void feedInput(DataContainer container, String data) {

        int projectId = Integer.parseInt(data);
        ProjectModel selectedProject = container.getProjects().get(projectId);
        if (selectedProject != null) {
            container.setSelectedProject(selectedProject);
        }
    }
}
