package com.sfinias.cli;

import com.sfinias.resource.TogglResource;
import java.time.LocalDate;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "toggl", mixinStandardHelpOptions = true, version = "toggl 1.0.0",
        description = "Interact with the Toggl API")
public class TogglCommand implements Callable<String> {

    private final TogglResource togglResource;

    @Option(names = {"-a", "--apikey"}, description = "Toggl API key", required = true)
    private String apiKey;

    @Option(names = {"-n", "--new-day"}, description = "Date for new entry")
    private LocalDate newDay = LocalDate.now();

    public TogglCommand(TogglResource togglResource) {

        this.togglResource = togglResource;
    }

    @Override
    public String call() throws Exception {

        return "Invalid command";
    }

    @Command(name = "copy", mixinStandardHelpOptions = true, version = "toggl 1.0.0",
            description = "Copies the entry of a past date")
    public String copy(@Option(names = {"-t", "--target-date"}, description = "Date which is copied") LocalDate targetDay) {

        this.togglResource.copyTimeEntriesOfDate(targetDay.toString());
        return "Done";
    }
}
