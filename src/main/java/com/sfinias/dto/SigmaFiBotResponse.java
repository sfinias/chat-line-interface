package com.sfinias.dto;

import com.sfinias.SigmaFiBot.ResponseType;

public class SigmaFiBotResponse {

    public final ResponseType type;
    public final String value;

    public SigmaFiBotResponse(ResponseType type, String value) {

        this.type = type;
        this.value = value;
    }
}
