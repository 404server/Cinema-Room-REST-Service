package com.example.exceptions;

import com.example.service.dto.SeatDto;
import lombok.Data;

@Data
public class HandleReturnTicket {
    private SeatDto seatDto;
    private boolean successful;
    private String error;

    public HandleReturnTicket(SeatDto seatDto, boolean successful) {
        this.seatDto = seatDto;
        this.successful = successful;
    }

    public HandleReturnTicket(String error, boolean successful) {
        this.error = error;
        this.successful = successful;
    }
}
