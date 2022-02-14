package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;

public abstract class ClientRequestStep extends AbstractStep {

    String requestPrompt;

    protected ClientRequestStep(String requestPrompt) {

        super(StepType.CLIENT_REQUEST);
        this.requestPrompt = requestPrompt;
    }

    public abstract void feedInput(DataContainer container, String data);
}
