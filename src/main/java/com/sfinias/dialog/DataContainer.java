package com.sfinias.dialog;

import com.sfinias.model.TimeEntryModel;
import java.time.LocalDate;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.User;

public class DataContainer {

    final User user;

    String apiKey;
    List<TimeEntryModel> oldEntries;
    LocalDate selectedDate;

    public DataContainer(User user) {

        this.user = user;
    }

    public User getUser() {

        return user;
    }

    public String getApiKey() {

        return apiKey;
    }

    public void setApiKey(String apiKey) {

        this.apiKey = apiKey;
    }

    public List<TimeEntryModel> getOldEntries() {

        return oldEntries;
    }

    public void setOldEntries(List<TimeEntryModel> oldEntries) {

        this.oldEntries = oldEntries;
    }

    public LocalDate getSelectedDate() {

        return selectedDate;
    }

    public void setSelectedDate(LocalDate selectedDate) {

        this.selectedDate = selectedDate;
    }
}
