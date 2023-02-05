package com.sfinias;

import com.sfinias.cli.CatCommand;
import com.sfinias.cli.ParentCommand;
import com.sfinias.cli.TogglCommand;
import com.sfinias.resource.CatResource;
import com.sfinias.resource.TogglResource;
import io.quarkus.logging.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
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
                    .setOut(writer).setErr(writer);
            cmd.execute(command.split(" "));
            if (out.toString().length() != 0) {
                sendMessage(user.getId(), out.toString());
            }
            String executionResult = cmd.getExecutionResult();
            if (executionResult != null) {
                if (executionResult.endsWith(".jpg") || executionResult.endsWith(".gif")) {
                    sendMediaResponse(user, executionResult);
                } else {
                    sendMessage(user.getId(), executionResult);
                }
            }
            ParseResult parseResult = cmd.getParseResult();
            if (parseResult.subcommand() != null) {
                CommandLine sub = parseResult.subcommand().commandSpec().commandLine();
                String subResult = sub.getExecutionResult();
                if (subResult != null) {
                    if (subResult.endsWith(".jpg") || subResult.endsWith(".gif")) {
                        sendMediaResponse(user, subResult);
                    } else {
                        sendMessage(user.getId(), subResult);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(user.getId(), e.getMessage());
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
