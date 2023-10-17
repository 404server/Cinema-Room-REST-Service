package com.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class Token {

    private UUID uuid;

    public Token(@JsonProperty("token") UUID token) {
        this.uuid = token;
    }
}
