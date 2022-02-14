package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.dialog.ServiceContainer;
import com.sfinias.model.ProjectModel;
import java.util.Map;
import java.util.stream.Collectors;

public class RetrieveProjects extends ApiRequestStep{

    @Override
    public boolean isApplicable(DataContainer container) {

        return this.status == RequestResult.PENDING;
    }

    @Override
    public void process(ServiceContainer serviceContainer, DataContainer dataContainer) {

        Map<Integer, ProjectModel> projects = serviceContainer.getTogglResource().getLunatechProjects().stream()
                .collect(Collectors.toMap(ProjectModel::getId, pr -> pr));
        if (!projects.isEmpty()) {
            dataContainer.setProjects(projects);
            this.status = RequestResult.SUCCESS;
        }
    }
}
