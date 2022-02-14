package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.dialog.ServiceContainer;

public abstract class SimpleClientRequest extends ClientRequestStep {

    String placeHolder;

    protected SimpleClientRequest(String requestPrompt, String placeHolder) {

        super(requestPrompt);
        this.placeHolder = placeHolder;
    }

    @Override
    public void process(ServiceContainer serviceContainer, DataContainer dataContainer) {

        serviceContainer.getBotResource().sendReply(dataContainer.getUser().getId(), requestPrompt, placeHolder);
    }
}
