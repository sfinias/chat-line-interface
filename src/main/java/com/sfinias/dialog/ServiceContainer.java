package com.sfinias.dialog;

import com.sfinias.resource.BotResource;
import com.sfinias.resource.TogglResource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ServiceContainer {

    @Inject
    BotResource botResource;

    @Inject
    TogglResource togglResource;

    public BotResource getBotResource() {

        return botResource;
    }

    public TogglResource getTogglResource() {

        return togglResource;
    }
}
