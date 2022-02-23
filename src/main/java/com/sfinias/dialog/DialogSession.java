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

    DialogIntent currentIntent;

    List<? extends AbstractStep> steps;

    public DialogSession(User user, ServiceContainer serviceContainer) {

        this.serviceContainer = serviceContainer;
        this.dataContainer = new DataContainer(user);
        setIntent(DialogIntent.ASK_INTENT);
    }

    public void process() {

        steps.stream()
                .filter(step -> step.isApplicable(dataContainer))
                .findFirst()
                .ifPresentOrElse(step -> {
                    step.process(serviceContainer, dataContainer);
                    status = step.getType() == StepType.CLIENT_REQUEST ? SessionStatus.WAITING : SessionStatus.ONGOING;
                }, this::completeIntent);
    }

    public void addInput(String message) {

        steps.stream()
                .filter(step -> step.isApplicable(dataContainer) && step.getType() == StepType.CLIENT_REQUEST)
                .findFirst()
                .map(ClientRequestStep.class::cast)
                .ifPresent(step -> {
                    step.feedInput(dataContainer, serviceContainer, message);
                    status = SessionStatus.ONGOING;
                });
    }

    private void completeIntent() {

        if (dataContainer.getIntent() == this.currentIntent) {
            status = SessionStatus.COMPLETED;
            dataContainer.setIntent(null);
            serviceContainer.getBotResource().sendMessage(dataContainer.getUser().getId(), "Task has completed successfully");
        } else {
            setIntent(dataContainer.getIntent());
        }
    }

    public void setIntent(DialogIntent intent) {

        this.currentIntent = intent;
        dataContainer.setIntent(intent);
        this.steps = intent.createDialogSteps();
    }

    public void clearTarget() {

        this.steps = Collections.emptyList();
    }

    public boolean hasNoTarget() {

        return this.dataContainer.getIntent() == null;
    }

    enum SessionStatus {
        ONGOING, WAITING, COMPLETED
    }
}
