package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class SelectDateStep extends SimpleClientRequest {

    public SelectDateStep() {

        super("Provide a past date", LocalDate.now().minusDays(1).toString());
    }

    @Override
    public void feedInput(DataContainer container, String dateInput) {

        if (dateInput == null || dateInput.trim().length() == 0) {
            throw new IllegalArgumentException("Date is empty");
        }
        LocalDate date;
        try {
            date = LocalDate.parse(dateInput);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date provided is not in the required format (yyyy-MM-dd)");
        }
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