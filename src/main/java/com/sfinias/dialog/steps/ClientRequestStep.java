package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.dialog.ServiceContainer;

public abstract class ClientRequestStep extends AbstractStep {

    String requestPrompt;

    protected ClientRequestStep(String requestPrompt) {

        super(StepType.CLIENT_REQUEST);
        this.requestPrompt = requestPrompt;
    }

    public final void feedInput(DataContainer dataContainer, ServiceContainer serviceContainer, String data) {

        try {
            feedInput(dataContainer, data);
        } catch (Exception e) {
            serviceContainer.getBotResource().sendMessage(dataContainer.getUser().getId(), e.getMessage());
        }
    }

    protected abstract void feedInput(DataContainer container, String data);
}
