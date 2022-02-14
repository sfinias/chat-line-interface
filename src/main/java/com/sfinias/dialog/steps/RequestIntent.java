package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import com.sfinias.dialog.DialogIntent;
import java.util.Arrays;
import java.util.stream.Collectors;

public class RequestIntent extends ListClientRequestStep {

    public RequestIntent() {

        super("Please choose an intent", container ->
                Arrays.stream(DialogIntent.values()).filter(intent -> intent != DialogIntent.ASK_INTENT)
                        .collect(Collectors.toMap(Enum::name, Enum::name))
        );
    }

    @Override
    public boolean isApplicable(DataContainer container) {

        return container.getIntent() == DialogIntent.ASK_INTENT;
    }

    @Override
    public void feedInput(DataContainer container, String data) {

        container.setIntent(DialogIntent.valueOf(data));
    }
}
