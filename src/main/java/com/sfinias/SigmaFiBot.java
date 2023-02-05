package com.sfinias;

import static com.sfinias.cli.TogglCommand.DATE_CONVERTER;

import com.sfinias.cli.CatCommand;
import com.sfinias.cli.ParentCommand;
import com.sfinias.cli.TogglCommand;
import com.sfinias.resource.CatResource;
import com.sfinias.resource.TogglResource;
import io.quarkus.logging.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
                    .setOut(writer).setErr(writer)
                    .registerConverter(LocalDate.class, DATE_CONVERTER);
            cmd.execute(command.split(" "));
            if (out.toString().length() != 0) {
                sendReply(user, out.toString());
            }
            extractedMessages(cmd).forEach(reply -> sendReply(user, reply));
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(user.getId(), e.getMessage());
        }
    }

    private List<String> extractedMessages(CommandLine cmd) {

        List<String> results = new ArrayList<>();
        while (cmd != null) {
            Optional.ofNullable(cmd.getExecutionResult()).map(String.class::cast).ifPresent(results::add);
            cmd = Optional.ofNullable(cmd.getParseResult()).map(ParseResult::subcommand).map(ParseResult::commandSpec).map(CommandSpec::commandLine).orElse(null);
        }
        return results;
    }

    public void sendReply(User user, String reply) {

        if (reply.endsWith(".jpg") || reply.endsWith(".gif")) {
            sendMediaResponse(user, reply);
        } else {
            sendMessage(user.getId(), reply);
        }
    }

    public void sendMessage(long userId, String requestMessage) {

        SendMessage sendMessage = SendMessage.builder()
                .text(requestMessage)
                .chatId(String.valueOf(userId))
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMediaResponse(User user, String url) {

        if (url.endsWith(".gif")) {
            sendAnimation(user, url);
        } else {
            sendPhoto(user, url);
        }
    }

    private void sendPhoto(User user, String url) {

        SendPhoto photo = SendPhoto.builder()
                .chatId(String.valueOf(user.getId()))
                .photo(new InputFile(url))
                .build();
        try {
            execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendAnimation(User user, String url) {

        SendAnimation animation = SendAnimation.builder()
                .chatId(String.valueOf(user.getId()))
                .animation(new InputFile(url))
                .build();
        try {
            execute(animation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private User extractUser(Update update) {

        return update.hasMessage() ? update.getMessage().getFrom() : update.getCallbackQuery().getFrom();
    }
}
