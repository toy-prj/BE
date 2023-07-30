package com.example.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "INVALID REQUEST, CHECK THE FORM"),
    TIME_OVER(HttpStatus.BAD_REQUEST, "CANNOT CANCEL THE TICKET, THE START TIME PASSED"),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "WRONG PASSWORD"),
    NOT_ALLOWED(HttpStatus.UNAUTHORIZED, "USER WITH NO PERMISSION"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER NOT FOUND"),
    MAIL_NOT_FOUND(HttpStatus.NOT_FOUND,"MAIL NOT FOUND"),
    MOVIE_NOT_FOUND(HttpStatus.NOT_FOUND,"MOVIE NOT FOUND"),
    THEATER_NOT_FOUND(HttpStatus.NOT_FOUND,"THEATER NOT FOUND"),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND,"SCHEDULE NOT FOUND"),
    SEAT_NOT_FOUND(HttpStatus.NOT_FOUND,"SEAT NOT FOUND"),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"PAYMENT NOT FOUND"),
    DUPLICATE_MAIL(HttpStatus.CONFLICT, "MAIL DOES EXIST ALREADY"),
    DUPLICATE_MOVIE(HttpStatus.CONFLICT, "MOVIE DOES EXIST ALREADY"),
    DUPLICATE_SEAT(HttpStatus.CONFLICT, "SEAT DOES EXIST ALREADY"),
    DUPLICATE_THEATER(HttpStatus.CONFLICT, "THEATER DOES EXIST ALREADY"),
    SAME_RANK(HttpStatus.CONFLICT, "THE RANK IS THE SAME AS BEFORE"),


    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "FAILURE IN PROCESS");

    private final HttpStatus httpStatus;
    private final String message;

}
