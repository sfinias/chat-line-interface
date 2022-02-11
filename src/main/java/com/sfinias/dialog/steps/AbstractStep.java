package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.dialog.ServiceContainer;

public abstract class AbstractStep {

    final StepType type;

    protected AbstractStep(StepType type) {

        this.type = type;
    }

    public StepType getType() {

        return type;
    }

    public abstract boolean isApplicable(DataContainer container);

    public abstract void process(ServiceContainer serviceContainer, DataContainer dataContainer);

    public enum StepType {
        CLIENT_REQUEST,
        API_REQUEST;
    }
}
