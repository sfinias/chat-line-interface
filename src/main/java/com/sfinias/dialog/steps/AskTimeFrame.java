package com.sfinias.dialog.steps;

import com.sfinias.dialog.DataContainer;
import io.smallrye.mutiny.tuples.Tuple2;
import java.time.LocalTime;

public class AskTimeFrame extends SimpleClientRequest{

    public static final String PROMPT = "Please add the time window";
    public static final String PH = "09:00-13:00";

    public AskTimeFrame() {

        super(PROMPT, PH);
    }

    @Override
    public boolean isApplicable(DataContainer container) {

        return container.getTimeWindow() == null;
    }

    @Override
    protected void feedInput(DataContainer container, String data) {

        String[] times = data.split("-");
        if (times.length != 2) return;
        LocalTime first = LocalTime.parse(times[0]);
        LocalTime second = LocalTime.parse(times[1]);
        if (first.isAfter(second)) return;
        container.setTimeWindow(Tuple2.of(first, second));
    }
}
