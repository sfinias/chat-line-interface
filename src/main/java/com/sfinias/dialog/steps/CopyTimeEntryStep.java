package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.dialog.ServiceContainer;

public class CopyTimeEntryStep extends ApiRequestStep {

    @Override
    public void callApi(ServiceContainer serviceContainer, DataContainer dataContainer) {
        serviceContainer.getTogglResource().copyTimeEntriesOfDate(dataContainer.getSelectedDate().toString());
    }
}
