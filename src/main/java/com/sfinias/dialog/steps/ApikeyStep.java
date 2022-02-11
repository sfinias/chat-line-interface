package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;

public class ApikeyStep extends ClientRequestStep {

    public ApikeyStep() {

        super("Please provide your API key", null);
    }

    @Override
    public void feedInput(DataContainer container, String apiKey) {

        if (keyIsEmpty(apiKey)) {
            throw new IllegalArgumentException("Key is empty");
        }
        container.setApiKey(apiKey);
    }

    @Override
    public boolean isApplicable(DataContainer container) {

        return keyIsEmpty(container.getApiKey());
    }

    private boolean keyIsEmpty(String apiKey) {

        return apiKey == null || apiKey.trim().length() == 0;
    }
}
