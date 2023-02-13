package io.sfinias.telegram.dto;

import io.sfinias.telegram.SigmaFiBot.ResponseType;

public class SigmaFiBotResponse {

    public final ResponseType type;
    public final String value;

    public SigmaFiBotResponse(ResponseType type, String value) {

        this.type = type;
        this.value = value;
    }
}
