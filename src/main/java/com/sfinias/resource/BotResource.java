package com.sfinias.resource;

import com.sfinias.SigmaFiBot;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Path("/bot")
public class BotResource {

    @Inject
    SigmaFiBot bot;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String startBot() {

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return "started";
    }
}