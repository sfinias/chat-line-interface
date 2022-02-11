package com.sfinias.dialog.steps;

public abstract class ApiRequestStep extends AbstractStep {

    protected RequestResult status = RequestResult.PENDING;

    protected ApiRequestStep() {

        super(StepType.API_REQUEST);
    }

    enum RequestResult{
        PENDING, SUCCESS;
    }
}
