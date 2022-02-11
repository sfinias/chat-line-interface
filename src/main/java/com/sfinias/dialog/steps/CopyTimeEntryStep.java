package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.dialog.ServiceContainer;

public class CopyTimeEntryStep extends ApiRequestStep {

    @Override
    public boolean isApplicable(DataContainer container) {

        return this.status == RequestResult.PENDING;
    }

    @Override
    public void process(ServiceContainer serviceContainer, DataContainer dataContainer) {
        serviceContainer.getTogglResource().copyTimeEntriesOfDate(dataContainer.getSelectedDate().toString());
        this.status = RequestResult.SUCCESS;
    }
}
