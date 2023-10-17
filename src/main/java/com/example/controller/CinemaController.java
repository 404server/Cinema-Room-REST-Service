package com.example.controller;

import com.example.CinemaRoomServiceApplication;
import com.example.controller.dto.Cinema;
import com.example.controller.dto.Seat;
import com.example.controller.dto.Token;
import com.example.exceptions.HandleBuyATicket;
import com.example.exceptions.HandleGetStatistic;
import com.example.service.CinemaService;
import com.example.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.exceptions.HandleReturnTicket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CinemaRoomServiceApplication.class);

    @GetMapping("/seats")
    public Cinema getSeats() {
        LOGGER.info("doGet method(/seats) deployed");
        return convertCinemaDto(cinemaService.getCinemaData());
    }

    @GetMapping("/stats")
    public ResponseEntity<HandleGetStatistic> getStats(@RequestParam(required = false) String pass) {
        LOGGER.info("doGet method(/stats) deployed");
        return cinemaService.getStats(pass);
    }

    @PostMapping("/purchase")
    public ResponseEntity<HandleBuyATicket> bookASeat(@RequestBody Seat seat) {
        LOGGER.info("doPost(/purchase) method deployed");
        return cinemaService.buyATicket(seat);
    }

    @PostMapping("/return")
    public ResponseEntity<HandleReturnTicket> returnTicket(@RequestBody Token token) {
        LOGGER.info("doPost(/return) method deployed");
        return cinemaService.returnTicket(token);
    }


    private Cinema convertCinemaDto(CinemaDto cinemaDto) {
        return new Cinema(cinemaDto.getTotalRows(), cinemaDto.getTotalColumns(),convertSeatsDto(cinemaDto.getAvailableSeats()));
    }

    private Seat convertSeatDto(SeatDto seatDto) {
        return new Seat(seatDto.getRow(), seatDto.getColumn());
    }

    private List<Seat> convertSeatsDto(List<SeatDto> seatsDto) {
        List<Seat> seats = new ArrayList<>();
        for (SeatDto seatDto : seatsDto) {
            seats.add(convertSeatDto(seatDto));
        }
        return seats;
    }


}
