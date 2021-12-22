package com.sfinias;

import static java.lang.Math.toIntExact;

import com.sfinias.model.CatModel;
import com.sfinias.resource.CatResource;
import io.quarkus.logging.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@ApplicationScoped
public class PreksfiBot extends TelegramLongPollingBot {

    @ConfigProperty(name = "sigmafi.apikey")
    String apikey;

    CatResource catResource;

    public PreksfiBot(CatResource catResource) {

        this.catResource = catResource;
    }

    public String getBotToken() {

        return apikey;
    }

    public void onUpdateReceived(Update update) {

        User user = extractUser(update);

        Log.info(user + ": " + update.getMessage().getText());

        if (update.hasMessage() && update.getMessage().hasText()) {

            long chatId = update.getMessage().getChatId();
            String command = update.getMessage().getText();
            switch (command) {
                case "/help":
                    help(update.getMessage().getChatId());
                    break;
                case "/cat":
                    sendCat(user);
                    break;
                case "/catgif":
                    sendCatGif(user);
                    break;
                case "/start":
                default:
                    start(user);
            }
        } else if (update.hasCallbackQuery()) {
            // Set variables
            String callData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String inlineChatId = update.getCallbackQuery().getInlineMessageId();
            System.out.println("MessageId : " + messageId + "\tChatId : " + chatId + "\tInlineChatId : " + inlineChatId);
            if (callData.equals("update_msg_text")) {
                updateMessage(messageId, chatId);
            } else {
//                Menu menu = this.menu.getNextMenu(callData);
            }
        }
    }

    private void updateMessage(long messageId, long chatId) {

        String answer = "Updated reply command";
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(InlineKeyboardButton.builder().text("Updated reply command").callbackData("Updated reply command").build());
        EditMessageText newMessage = EditMessageText.builder()
                .chatId(String.valueOf(chatId))
                .messageId(toIntExact(messageId))
                .text(answer)
                .replyMarkup(markupInline)
                .build();
        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void start(User user) {

        SendMessage reply = SendMessage.builder()
                .text("Hi " + user.getFirstName() + ", only option right now is /cat")
                .chatId(String.valueOf(user.getId()))
                .build();
        try {
            execute(reply);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void chooseMenu(InlineKeyboardMarkup keyboardMenu, long id, long messageId) {

        EditMessageReplyMarkup reply = EditMessageReplyMarkup.builder()
                .chatId(String.valueOf(id)).replyMarkup(keyboardMenu).messageId(toIntExact(messageId)).build();
        try {
            execute(reply);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void help(long id) {

        SendMessage reply = new SendMessage();
        reply.setText("Choose 1 of the below options");
        reply.setChatId(String.valueOf(id));
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(InlineKeyboardButton.builder().text("Update reply command").callbackData("update_msg_text").build());
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the reply
        markupInline.setKeyboard(rowsInline);
        reply.setReplyMarkup(markupInline);
        try {
            execute(reply);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {

        return "PresfiBot";
    }

    private boolean unauthorizedAccess(User user) {

        if (!Arrays.asList("sfinias", "prekss", "AsAboveSoBelow33").contains(user.getUserName())) {
            SendAnimation animation = SendAnimation.builder()
                    .chatId(String.valueOf(user.getId().longValue()))
                    .animation(new InputFile("https://media1.tenor.com/images/0a691bb0ff447ac0a6a3b1e3bfa46265/tenor.gif"))
                    .build();
            try {
                execute(animation);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private void sendCat(User user) {

        CatModel cat = catResource.getRandomCat();
        Log.info("Cat received: " + cat);
        String url = cat.getUrl();
        if (url.endsWith(".gif")) {
            sendAnimation(user, url);
        } else {
            sendPhoto(user, url);
        }
    }

    private void sendPhoto(User user, String url) {
        SendPhoto photo = SendPhoto.builder()
                .chatId(String.valueOf(user.getId().longValue()))
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
                .chatId(String.valueOf(user.getId().longValue()))
                .animation(new InputFile(url))
                .build();
        try {
            execute(animation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendCatGif(User user) {

        CatModel cat = catResource.getRandomCatGif();
        Log.info("Cat gif received: " + cat);
        sendAnimation(user, cat.getUrl());
    }

    private User extractUser(Update update) {

        return update.hasMessage() ? update.getMessage().getFrom() : update.getCallbackQuery().getFrom();
    }
}
