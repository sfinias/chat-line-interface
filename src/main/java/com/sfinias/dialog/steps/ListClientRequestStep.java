package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.dialog.ServiceContainer;
import java.util.Map;
import java.util.function.Function;

public abstract class ListClientRequestStep extends ClientRequestStep{

    Function<DataContainer, Map<String, String>> function;

    protected ListClientRequestStep(String requestPrompt, Function<DataContainer, Map<String, String>> function) {

        super(requestPrompt);
        this.function = function;
    }

    @Override
    public void process(ServiceContainer serviceContainer, DataContainer dataContainer) {

        serviceContainer.getBotResource().sendOptions(dataContainer.getUser().getId(), requestPrompt, function.apply(dataContainer));
    }
}
