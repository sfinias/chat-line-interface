package com.sfinias.cli;

import com.sfinias.SigmaFiBot.ResponseType;
import com.sfinias.dto.SigmaFiBotResponse;
import com.sfinias.dto.TogglTimeEntry;
import com.sfinias.resource.TogglResource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import picocli.CommandLine.Command;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.Option;

@Command(name = "toggl", mixinStandardHelpOptions = true, version = "toggl 1.0.0",
        description = "Interact with the Toggl API")
public class TogglCommand {

    private final TogglResource togglResource;

    public static final ITypeConverter<LocalDate> DATE_CONVERTER = value -> LocalDate.parse(value, DateTimeFormatter.ofPattern("d-M-yyyy"));

    @Option(names = {"-a", "--apikey"}, description = "Toggl API key")
    private String apiKey;

    public TogglCommand(TogglResource togglResource) {

        this.togglResource = togglResource;
    }

    @Command(name = "copy", mixinStandardHelpOptions = true, version = "toggl 1.0.0",
            description = "Copies the entry of a past date")
    public SigmaFiBotResponse copy(
            @Option(names = {"-t", "--target-date"}, description = "Date which is copied, format: d-M-yyyy", required = true) LocalDate targetDay,
            @Option(names = {"-n", "--new-day"}, description = "Date for new entry, format: d-M-yyyy, default: current day") Optional<LocalDate> newDay) {

        List<TogglTimeEntry> newEntries = this.togglResource.copyTimeEntriesOfDate(targetDay.toString(), newDay.orElseGet(LocalDate::now).toString());
        return new SigmaFiBotResponse(ResponseType.TEXT, "Created following entries\n" + newEntries);
    }
}
