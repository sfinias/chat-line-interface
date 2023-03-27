package io.sfinias.cli.telegram.dto;

import io.sfinias.cli.telegram.SigmaFiBot.ResponseType;

public class SigmaFiBotResponse {

    public final ResponseType type;
    public final String value;

    public SigmaFiBotResponse(ResponseType type, String value) {

        this.type = type;
        this.value = value;
    }
}
