package com.example.exceptions;

import com.example.service.dto.SeatDto;
import lombok.Data;

import java.util.UUID;

@Data
public class HandleBuyATicket {

    private SeatDto seatDto;
    private UUID uuid;
    private boolean successful;
    private String error;

    public HandleBuyATicket(SeatDto seatDto, UUID uuid,boolean successful) {
        this.seatDto = seatDto;
        this.successful = successful;
        this.uuid = uuid;
    }

    public HandleBuyATicket(String error, boolean successful) {
        this.error = error;
        this.successful = successful;
    }
}
