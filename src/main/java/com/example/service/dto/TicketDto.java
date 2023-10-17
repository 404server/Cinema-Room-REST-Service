package com.example.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TicketDto {
    private UUID token;
    private SeatDto ticket;

    public TicketDto(UUID uuid, SeatDto seat) {
        this.token = uuid;
        this.ticket = seat;
    }


}
