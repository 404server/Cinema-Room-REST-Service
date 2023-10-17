package com.example.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({
        "current_income",
        "number_of_available_seats",
        "number_of_purchased_tickets",
})

@Data
public class HandleGetStatistic {
    @JsonProperty("current_income")
    private long currentIncome;
    @JsonProperty("number_of_available_seats")
    private int numberOfAvailableSeats;
    @JsonProperty("number_of_purchased_tickets")
    private int numberOfPurchasedTickets;

    private boolean successful;
    private String error;

    public HandleGetStatistic(long current_income, int number_of_available_seats, int number_of_purchased_tickets, boolean successful) {
        this.currentIncome = current_income;
        this.numberOfAvailableSeats = number_of_available_seats;
        this.numberOfPurchasedTickets = number_of_purchased_tickets;
        this.successful = successful;
    }

    public HandleGetStatistic(String error, boolean successful) {
        this.error = error;
        this.successful = successful;
    }
}
