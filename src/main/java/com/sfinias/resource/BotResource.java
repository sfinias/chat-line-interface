package com.sfinias.resource;

import com.sfinias.SigmaFiBot;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
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

    @GET
    @Path("/reply")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer sendReply(@QueryParam long userId, @QueryParam String message, @QueryParam String placeholder) {

        return this.bot.sendClientRequest(userId, message, placeholder);
    }

    @GET
    @Path("/message")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer sendMessage(@QueryParam long userId, @QueryParam String message) {

        return this.bot.sendMessage(userId, message);
    }

    @POST
    @Path("/options")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Integer sendOptions(long userId, String message, Map<String, String> options) {

        return this.bot.sendKeyboardOptions(userId, message, options);
    }
}