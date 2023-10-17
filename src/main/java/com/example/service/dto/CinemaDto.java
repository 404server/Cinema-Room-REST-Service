package com.example.service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CinemaDto {
    private int totalRows;
    private int totalColumns;
    private List<SeatDto> availableSeats;

    public CinemaDto(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = new ArrayList<>();
        for (int row = 1; row <= totalRows; row++) {
            for (int column = 1; column <= totalColumns; column++) {
                this.availableSeats.add(new SeatDto(row, column));
            }
        }
    }

}

