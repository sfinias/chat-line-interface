package com.sfinias;

import static com.sfinias.cli.TogglCommand.DATE_CONVERTER;

import com.sfinias.cli.CatCommand;
import com.sfinias.cli.MemeCommand;
import com.sfinias.cli.ParentCommand;
import com.sfinias.cli.TogglCommand;
import com.sfinias.dto.SigmaFiBotResponse;
import com.sfinias.resource.CatResource;
import com.sfinias.resource.MemeResource;
import com.sfinias.resource.TogglResource;
import com.sfinias.util.ExceptionHandler;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.tuples.Functions.Function3;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.ParseResult;

@ApplicationScoped
public class SigmaFiBot extends TelegramLongPollingBot {

    @ConfigProperty(name = "sigmafi.apikey")
    String apikey;

    @Inject
    CatResource catResource;

    @Inject
    TogglResource togglResource;

    @Inject
    MemeResource memeResource;

    @Override
    public String getBotToken() {

        return apikey;
    }

    @Override
    public String getBotUsername() {

        return this.getClass().getSimpleName();
    }

    public void onUpdateReceived(Update update) {

        User user = extractUser(update);
        if (update.hasMessage()) {
            handleNewMessage(user, update.getMessage());
        }
    }

    private void handleNewMessage(User user, Message message) {

        Log.info(user + ": " + message.getText());
        String command = message.getText();
        try (StringWriter out = new StringWriter();
                PrintWriter writer = new PrintWriter(out)) {
            CommandLine cmd = new CommandLine(new ParentCommand())
                    .addSubcommand(new TogglCommand(togglResource))
                    .addSubcommand(new CatCommand(catResource))
                    .addSubcommand(new MemeCommand(memeResource))
                    .setOut(writer).setErr(writer)
                    .setExecutionExceptionHandler(new ExceptionHandler())
                    .registerConverter(LocalDate.class, DATE_CONVERTER);
            cmd.execute(command.split(" "));
            if (out.toString().length() != 0) {
                ResponseType.TEXT.combiningFunction.apply(this, user, out.toString());
            }
            extractedMessages(cmd).forEach(response -> response.type.combiningFunction.apply(this, user, response.value));
        } catch (Exception e) {
            Log.error("There was an error", e);
            ResponseType.TEXT.combiningFunction.apply(this, user, e.getMessage());
        }
    }

    private List<SigmaFiBotResponse> extractedMessages(CommandLine cmd) {

        List<SigmaFiBotResponse> results = new ArrayList<>();
        while (cmd != null) {
            Optional.ofNullable(cmd.getExecutionResult()).map(SigmaFiBotResponse.class::cast).ifPresent(results::add);
            cmd = Optional.ofNullable(cmd.getParseResult()).map(ParseResult::subcommand).map(ParseResult::commandSpec).map(CommandSpec::commandLine).orElse(null);
        }
        return results;
    }
    private User extractUser(Update update) {

        return update.hasMessage() ? update.getMessage().getFrom() : update.getCallbackQuery().getFrom();
    }

    public enum ResponseType {
        TEXT((user, text) ->  SendMessage.builder()
                    .text(text)
                    .chatId(String.valueOf(user.getId()))
                    .build(),
                (bot, message) -> {
                    try {
                        return bot.execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }),
        IMAGE((user, image) -> SendPhoto.builder()
                .chatId(String.valueOf(user.getId()))
                .photo(new InputFile(image))
                .build(),
                (bot, message) -> {
                    try {
                        return bot.execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }),
        VIDEO((user, video) -> SendAnimation.builder()
                .chatId(String.valueOf(user.getId()))
                .animation(new InputFile(video))
                .build(),
                (bot, message) -> {
                    try {
                        return bot.execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                });

        public final Function3<SigmaFiBot, User, String, Message> combiningFunction;

        <T extends PartialBotApiMethod<Message>> ResponseType(BiFunction<User, String, T> function, BiFunction<SigmaFiBot, T, Message> executionFunction) {

            this.combiningFunction = (bot, user, message) -> executionFunction.apply(bot, function.apply(user, message));
        }
    }
}
