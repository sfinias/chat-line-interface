package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;

public class AddDescription extends SimpleClientRequest {

    public static final String PROMPT = "Please add a description";
    public static final String PLACEHOLDER = "Work on <ticket>";

    public AddDescription() {

        super(PROMPT, PLACEHOLDER);
    }

    @Override
    public boolean isApplicable(DataContainer container) {

        return container.getDescription() == null;
    }

    @Override
    protected void feedInput(DataContainer container, String description) {

        if (description == null || description.trim().length() == 0) return;
        container.setDescription(description);
    }
}
