package io.sfinias.cli.telegram.resource;

import io.quarkus.logging.Log;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.Startup;
import io.sfinias.cli.telegram.SigmaFiBot;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Path("/callback")
public class BotResource {

    final SigmaFiBot bot;

    public BotResource(SigmaFiBot bot) {

        this.bot = bot;
    }

    @Startup
    public void runOnDev() {

        if (LaunchMode.current() == LaunchMode.DEVELOPMENT) {
            Log.info("Running in DEV, starting bot in LongPolling mode");
            try {
                TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
                telegramBotsApi.registerBot(bot);
            } catch (TelegramApiException e) {
                Log.error("Exception during long polling", e);
            }
        }
    }

    @POST
    @Path("/bot")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response updateReceived(Update update) {

        BotApiMethod<?> response = bot.onWebhookUpdateReceived(update);
        return Response.ok(response).build();
    }

    @GET
    @Path("/sigma-fi")
    @Produces({"application/json"})
    public String testReceived() {

        return "Hi there ";
    }

}
