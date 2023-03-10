package io.sfinias.cli.telegram.commands;

import io.sfinias.cli.telegram.SigmaFiBot.ResponseType;
import io.sfinias.cli.telegram.dto.SigmaFiBotResponse;
import io.sfinias.cli.toggl.TogglResource;
import io.sfinias.cli.toggl.dto.TogglCreateNewEntry;
import io.sfinias.cli.toggl.dto.TogglTimeEntry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        List<TogglTimeEntry> newEntries = this.togglResource.copyTimeEntriesOfDate(targetDay, newDay.orElseGet(LocalDate::now));
        return new SigmaFiBotResponse(ResponseType.TEXT, "Created following entries\n" + newEntries);
    }

    @Command(name = "create", mixinStandardHelpOptions = true, version = "toggl 1.0.0",
            description = "Creates a new entry")
    public SigmaFiBotResponse create(
            @Option(names = {"-s", "--start"}, description = "Starting time of the entry, format: hh:mm", required = true) LocalTime start,
            @Option(names = {"-e", "--end"}, description = "Ending time of the entry, format: hh:mm", required = true) LocalTime end,
            @Option(names = {"-p", "--project"}, description = "Project name, does not have to post the whole name", required = true) String projectName,
            @Option(names = {"-d", "--description"}, description = "Description of the new entry", required = true) String description,
            @Option(names = {"-n", "--new-day"}, description = "Date for new entry, format: d-M-yyyy, default: current day") Optional<LocalDate> newDay
    ) {

        LocalDate date = newDay.orElseGet(LocalDate::now);
        TogglCreateNewEntry newEntry = new TogglCreateNewEntry(description, projectName, LocalDateTime.of(date, start), LocalDateTime.of(date, end));
        TogglTimeEntry createdEntry = this.togglResource.createNewEntry(newEntry);
        return new SigmaFiBotResponse(ResponseType.TEXT, "Created following entry" + createdEntry);
    }
}
