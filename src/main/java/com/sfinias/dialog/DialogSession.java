package com.sfinias.dialog;

import com.sfinias.dialog.steps.AbstractStep;
import com.sfinias.dialog.steps.AbstractStep.StepType;
import com.sfinias.dialog.steps.ClientRequestStep;
import java.util.Collections;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.User;

public class DialogSession {

    ServiceContainer serviceContainer;

    DataContainer dataContainer;

    SessionStatus status = SessionStatus.ONGOING;

    List<? extends AbstractStep> steps;

    public DialogSession(User user, ServiceContainer serviceContainer) {

        this.serviceContainer = serviceContainer;
        this.dataContainer = new DataContainer(user);
    }

    public void process() {

        steps.stream()
                .filter(step -> step.isApplicable(dataContainer))
                .findFirst()
                .ifPresentOrElse(step -> {
                    step.process(serviceContainer, dataContainer);
                    status = step.getType() == StepType.CLIENT_REQUEST ? SessionStatus.WAITING : SessionStatus.ONGOING;
                }, () -> {
                    status = SessionStatus.COMPLETED;
                    completeSession();
                });
    }

    public void addInput(String message) {

        steps.stream()
                .filter(step -> step.isApplicable(dataContainer) && step.getType() == StepType.CLIENT_REQUEST)
                .findFirst()
                .map(ClientRequestStep.class::cast)
                .ifPresent(step -> {
                    step.feedInput(dataContainer, message);
                    status = SessionStatus.ONGOING;
                });
    }

    private void completeSession() {

        serviceContainer.getBotResource().sendMessage(dataContainer.getUser().getId(), "Task has completed successfully");
    }

    public void setTarget(DialogIntent target) {

        this.steps = target.createNewSession();
    }

    public void clearTarget() {

        this.steps = Collections.emptyList();
    }

    public boolean hasNoTarget() {

        return this.steps == null || this.steps.isEmpty();
    }

    enum SessionStatus {
        ONGOING, WAITING, COMPLETED
    }
}
