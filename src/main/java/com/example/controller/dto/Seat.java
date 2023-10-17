package com.example.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Seat {
    private int row;
    private int column;
    private int price;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = row <= 4 ? 10 : 8;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Seat) {
            Seat seat = (Seat) obj;
            return obj != null && seat.getRow() == this.row && seat.getColumn() == this.column;
        }
        return false;
    }

}
