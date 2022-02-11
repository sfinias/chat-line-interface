package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.dialog.ServiceContainer;

public abstract class ClientRequestStep extends AbstractStep {

    String requestPrompt;

    String placeHolder;

    protected ClientRequestStep(String requestPrompt, String placeHolder) {

        super(StepType.CLIENT_REQUEST);
        this.requestPrompt = requestPrompt;
        this.placeHolder = placeHolder;
    }

    @Override
    public void process(ServiceContainer serviceContainer, DataContainer dataContainer) {

        serviceContainer.getBotResource().sendReply(dataContainer.getUser().getId(), requestPrompt, placeHolder);
    }


    public abstract void feedInput(DataContainer container, String data);
}
