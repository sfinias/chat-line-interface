package com.sfinias.dialog;

import static com.sfinias.dialog.steps.StepEnum.ASK_APIKEY;
import static com.sfinias.dialog.steps.StepEnum.COPY_TIME_ENTRY;
import static com.sfinias.dialog.steps.StepEnum.SELECT_DATE;

import com.sfinias.dialog.steps.AbstractStep;
import com.sfinias.dialog.steps.StepEnum;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DialogIntent {
    COPY_PAST_DAY(ASK_APIKEY, SELECT_DATE, COPY_TIME_ENTRY);

    private final List<StepEnum> steps;

    DialogIntent(StepEnum... steps) {

        this.steps = Arrays.stream(steps).collect(Collectors.toList());
    }

    public List<AbstractStep> createNewSession() {

        return steps.stream()
                .map(StepEnum::createStep)
                .collect(Collectors.toList());
    }
}
