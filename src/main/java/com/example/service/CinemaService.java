package com.example.service;

import com.example.CinemaRoomServiceApplication;
import com.example.controller.dto.Seat;
import com.example.controller.dto.Token;
import com.example.exceptions.HandleBuyATicket;
import com.example.exceptions.HandleGetStatistic;
import com.example.exceptions.HandleReturnTicket;
import com.example.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CinemaService {
    private final int numberOfRows;
    private final int numberOfColumns;
    private final String password;

    private final ConcurrentHashMap<UUID, SeatDto> bookedTickets = new ConcurrentHashMap<>();
    private long income = 0;
    private final CinemaDto cinemaRoom;
    private static final Logger LOGGER = LoggerFactory.getLogger(CinemaRoomServiceApplication.class);

    CinemaService(@Value("${cinema.rows}") int numberOfRows,
                  @Value("${cinema.columns}") int numberOfColumns,
                  @Value("${password}") String password) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.password = password;
        this.cinemaRoom = new CinemaDto(numberOfRows,numberOfColumns);
    }


    public CinemaDto getCinemaData() {
        return cinemaRoom;
    }


    public ResponseEntity<HandleBuyATicket> buyATicket(Seat seat) {
        SeatDto bookedTicket = new SeatDto(seat.getRow(), seat.getColumn());
        UUID uuid = UUID.randomUUID();

        if (seat.getRow() <= 0 || seat.getColumn() <= 0 || (seat.getRow() > numberOfRows || seat.getColumn() > numberOfColumns)) {
            LOGGER.error("The number of a row or a column is out of bounds!");
            return new ResponseEntity<>(new HandleBuyATicket("The number of a row or a column is out of bounds!",false), HttpStatus.BAD_REQUEST);
        }

        for (int i = 0; i < cinemaRoom.getAvailableSeats().size(); i++) {
            SeatDto s = cinemaRoom.getAvailableSeats().get(i);
            if (s.equals(bookedTicket)) {
                cinemaRoom.getAvailableSeats().remove(i);
                income += bookedTicket.getPrice();
                TicketDto ticketDto = new TicketDto(uuid, bookedTicket);
                bookedTickets.put(uuid, bookedTicket);
                LOGGER.info("Ticket purchased successfully");
                return new ResponseEntity<>(new HandleBuyATicket(ticketDto.getTicket(),ticketDto.getToken(),true), HttpStatus.CREATED);
            }
        }
        LOGGER.error("The ticket has been already purchased!");
        return new ResponseEntity<>(new HandleBuyATicket("The ticket has been already purchased!",false), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<HandleReturnTicket> returnTicket(Token token) {
        for (UUID uuid : bookedTickets.keySet()) {
            if (token.getUuid().equals(uuid)) {
                cinemaRoom.getAvailableSeats().add(bookedTickets.get(uuid));
                SeatDto returnedSeat = bookedTickets.get(uuid);
                income -= returnedSeat.getPrice();
                bookedTickets.remove(uuid);
                LOGGER.info("The ticket has been returned successfully");
                return new ResponseEntity<>(new HandleReturnTicket(returnedSeat,true), HttpStatus.OK);
            }
        }
        LOGGER.error("Wrong token for ticket.Unable to return ticket");
        return new ResponseEntity<>(new HandleReturnTicket("Wrong token!",false), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<HandleGetStatistic> getStats(String pass) {
        if (pass == null) {
            LOGGER.error("Password is null.Try Again!");
            return new ResponseEntity<>(new HandleGetStatistic("The password is wrong!",false), HttpStatus.UNAUTHORIZED);
        }
        if (pass.equals(password)) {
            LOGGER.info("Getting stat successfully");
            return new ResponseEntity<>(new HandleGetStatistic(income,cinemaRoom.getAvailableSeats().size(),bookedTickets.size(),true)
                    , HttpStatus.OK);
        }
        LOGGER.error("Password is wrong!.Try Again!");
        return new ResponseEntity<>(new HandleGetStatistic("The password is wrong!",false), HttpStatus.UNAUTHORIZED);
    }


}