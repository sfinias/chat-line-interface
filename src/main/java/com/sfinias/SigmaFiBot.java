package com.sfinias;

import com.sfinias.model.CatModel;
import com.sfinias.model.MemeModel;
import com.sfinias.resource.CatResource;
import com.sfinias.resource.MemeResource;
import io.quarkus.logging.Log;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@ApplicationScoped
public class SigmaFiBot extends TelegramLongPollingBot {

    @ConfigProperty(name = "sigmafi.apikey")
    String apikey;

    CatResource catResource;

    MemeResource memeResource;

    public SigmaFiBot(CatResource catResource, MemeResource memeResource) {

        this.catResource = catResource;
        this.memeResource = memeResource;
    }

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

        Log.info(user + ": " + update.getMessage().getText());

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        String command = update.getMessage().getText();
        switch (command) {
            case "/cat":
                sendCat(user);
                break;
            case "/catgif":
                sendCatGif(user);
                break;
            case "/meme":
                sendMeme(user);
                break;
            case "/start":
            default:
                start(user);
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

    private void sendCatGif(User user) {

        CatModel cat = catResource.getRandomCatGif();
        Log.info("Cat gif received: " + cat);
        sendAnimation(user, cat.getUrl());
    }

    private void sendMeme(User user) {

        MemeModel meme = memeResource.getRandomMeme();
        Log.info("Meme received: " + meme);
        String url = meme.getUrl();
        sendPhoto(user, url);
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

    private User extractUser(Update update) {

        return update.hasMessage() ? update.getMessage().getFrom() : update.getCallbackQuery().getFrom();
    }
}
