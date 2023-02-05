package com.sfinias.cli;

import com.sfinias.dto.TogglTimeEntry;
import com.sfinias.resource.TogglResource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.Option;

@Command(name = "toggl", mixinStandardHelpOptions = true, version = "toggl 1.0.0",
        description = "Interact with the Toggl API")
public class TogglCommand implements Callable<String> {

    private final TogglResource togglResource;

    public static final ITypeConverter<LocalDate> DATE_CONVERTER = value -> LocalDate.parse(value, DateTimeFormatter.ofPattern("d-M-yyyy"));

    @Option(names = {"-a", "--apikey"}, description = "Toggl API key")
    private String apiKey;

    public TogglCommand(TogglResource togglResource) {

        this.togglResource = togglResource;
    }

    @Override
    public String call() throws Exception {

        return "Invalid command";
    }

    @Command(name = "copy", mixinStandardHelpOptions = true, version = "toggl 1.0.0",
            description = "Copies the entry of a past date")
    public String copy(
            @Option(names = {"-t", "--target-date"}, description = "Date which is copied, format: d-M-yyyy", required = true) LocalDate targetDay,
            @Option(names = {"-n", "--new-day"}, description = "Date for new entry, format: d-M-yyyy, default: current day") LocalDate newDay) {

        List<TogglTimeEntry> newEntries = this.togglResource.copyTimeEntriesOfDate(targetDay.toString(), (newDay != null ? newDay : LocalDate.now()).toString());
        return "Created following entries\n" + newEntries;
    }
}
