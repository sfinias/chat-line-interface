package io.sfinias.cli.telegram.resource;

import io.quarkus.runtime.Startup;
import io.sfinias.cli.telegram.SigmaFiBot;
import javax.enterprise.context.ApplicationScoped;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Startup
@ApplicationScoped
public class BotResource {

    public BotResource(SigmaFiBot bot) {

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}