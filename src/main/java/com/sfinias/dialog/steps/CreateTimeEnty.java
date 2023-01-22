package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.dialog.ServiceContainer;
import com.sfinias.model.TimeEntryModel;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CreateTimeEnty extends ApiRequestStep{

    @Override
    public void callApi(ServiceContainer serviceContainer, DataContainer dataContainer) {

        TimeEntryModel newTimeEntry = new TimeEntryModel();
        newTimeEntry.setDescription(dataContainer.getDescription());
        newTimeEntry.setDuration((long)(dataContainer.getTimeWindow().getItem2().toSecondOfDay() - dataContainer.getTimeWindow().getItem1().toSecondOfDay()));
        newTimeEntry.setBillable(dataContainer.getSelectedProject().isBillable());
        newTimeEntry.setStart(ZonedDateTime.of(LocalDate.now(), dataContainer.getTimeWindow().getItem1(), ZoneId.systemDefault()).toInstant());
        newTimeEntry.setPid((long) dataContainer.getSelectedProject().getId());
        newTimeEntry.setCreatedWith("SigmaFiBot");
        serviceContainer.getTogglResource().createNewEntry(newTimeEntry);
    }
}
