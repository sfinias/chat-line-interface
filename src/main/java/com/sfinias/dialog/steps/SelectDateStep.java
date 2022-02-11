package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import java.time.LocalDate;

public class SelectDateStep extends ClientRequestStep {

    public SelectDateStep() {

        super("Provide a past date", LocalDate.now().minusDays(1).toString());
    }

    @Override
    public void feedInput(DataContainer container, String dateInput) {

        if (dateInput == null || dateInput.trim().length() == 0) {
            throw new IllegalArgumentException("Date is empty");
        }
        LocalDate date = LocalDate.parse(dateInput);
        if (!date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date is not in the past");
        }
        container.setSelectedDate(date);
    }

    @Override
    public boolean isApplicable(DataContainer container) {

        return container.getSelectedDate() == null;
    }
}