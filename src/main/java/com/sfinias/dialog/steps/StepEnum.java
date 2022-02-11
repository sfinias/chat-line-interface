package com.sfinias.dialog.steps;

import java.util.function.Supplier;

public enum StepEnum {
    ASK_APIKEY(ApikeyStep::new),
    SELECT_DATE(SelectDateStep::new),
    COPY_TIME_ENTRY(CopyTimeEntryStep::new);

    private final Supplier<? extends AbstractStep> supplier;

    StepEnum(Supplier<? extends AbstractStep> supplier) {

        this.supplier = supplier;
    }

    public AbstractStep createStep() {

        return supplier.get();
    }
}
