package com.sfinias.dialog;

import static com.sfinias.dialog.steps.StepEnum.ASK_APIKEY;
import static com.sfinias.dialog.steps.StepEnum.CHOOSE_PROJECT;
import static com.sfinias.dialog.steps.StepEnum.COPY_TIME_ENTRY;
import static com.sfinias.dialog.steps.StepEnum.REQUEST_INTENT;
import static com.sfinias.dialog.steps.StepEnum.RETRIEVE_PROJECTS;
import static com.sfinias.dialog.steps.StepEnum.SELECT_DATE;

import com.sfinias.dialog.steps.AbstractStep;
import com.sfinias.dialog.steps.StepEnum;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DialogIntent {
    COPY_PAST_DAY(ASK_APIKEY, SELECT_DATE, COPY_TIME_ENTRY),
    NEW_ENTRY(ASK_APIKEY, RETRIEVE_PROJECTS, CHOOSE_PROJECT),
    ASK_INTENT(REQUEST_INTENT);

    private final List<StepEnum> steps;

    DialogIntent(StepEnum... steps) {

        this.steps = Arrays.stream(steps).collect(Collectors.toList());
    }

    public List<AbstractStep> createDialogSteps() {

        return steps.stream()
                .map(StepEnum::createStep)
                .collect(Collectors.toList());
    }
}
