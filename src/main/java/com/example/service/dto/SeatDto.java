package com.example.service.dto;

import lombok.Data;

@Data
public class SeatDto {
    private int row;
    private int column;
    private int price;

    public SeatDto(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = row <= 4 ? 10 : 8;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SeatDto) {
            SeatDto seatDto = (SeatDto) obj;
            return obj != null && seatDto.getRow() == this.row && seatDto.getColumn() == this.column;
        }
        return false;
    }


}
