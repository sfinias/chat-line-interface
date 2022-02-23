package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.dialog.ServiceContainer;

public abstract class ApiRequestStep extends AbstractStep {

    protected RequestResult status = RequestResult.PENDING;

    protected ApiRequestStep() {

        super(StepType.API_REQUEST);
    }

    @Override
    public final boolean isApplicable(DataContainer container) {

        return this.status == RequestResult.PENDING;
    }

    @Override
    public final void process(ServiceContainer serviceContainer, DataContainer dataContainer) {

        try {
            callApi(serviceContainer, dataContainer);
        } catch (Exception e) {
            serviceContainer.getBotResource().sendMessage(dataContainer.getUser().getId(), "Webservice error");
        }
        this.status = RequestResult.SUCCESS;
    }

    public abstract void callApi(ServiceContainer serviceContainer, DataContainer dataContainer);

    enum RequestResult{
        PENDING, SUCCESS;
    }
}
