package com.sfinias.dialog;

import com.sfinias.dialog.DialogSession.SessionStatus;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.telegram.telegrambots.meta.api.objects.User;

@ApplicationScoped
public class DialogSessionHandler {

    @Inject
    ServiceContainer serviceContainer;

    Map<User, DialogSession> dialogSessions = new HashMap<>();

    public void createNewSession(User user) {

        DialogSession session = new DialogSession(user, serviceContainer);
        dialogSessions.put(user, session);
        process(user);
    }

    public void process(User user) {

        DialogSession session = dialogSessions.getOrDefault(user, new DialogSession(user, serviceContainer));
        dialogSessions.put(user, session);
        while (session.status == SessionStatus.ONGOING) {
            session.process();
        }
        if (session.status == SessionStatus.COMPLETED) {
            dialogSessions.remove(user);
        }
    }

    public void addInput(User user, String message) {

        dialogSessions.get(user).addInput(message);
        process(user);
    }

    public boolean hasActiveSession(User user) {

        DialogSession session = dialogSessions.get(user);
        return session != null && !session.hasNoTarget();
    }
}
