package com.railways.reservation.system.exception;

import lombok.*;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "Email already exists", HttpStatus.BAD_REQUEST),
    PHONE_ALREADY_EXISTS("PHONE_ALREADY_EXISTS", "An account with this phone number already exists", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH", "The password and confirmation do not match", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", "Role not found", HttpStatus.NOT_FOUND),
    INVALID_CURRENT_PASSWORD("INVALID_CURRENT_PASSWORD", "The current password is incorrect", HttpStatus.BAD_REQUEST),
    CHANGE_PASSWORD_MISMATCH("CHANGE_PASSWORD_MISMATCH", "The new password and confirmation do not match", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_DEACTIVATED("ACCOUNT_ALREADY_DEACTIVATED", "The account is already deactivated", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_ACTIVATED("ACCOUNT_ALREADY_ACTIVATED", "The account is already activated", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("UNAUTHORIZED", "Unauthorized access", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN_TYPE("INVALID_TOKEN_TYPE", "Invalid token type", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_EXPIRED("REFRESH_TOKEN_EXPIRED", "Refresh token has expired", HttpStatus.UNAUTHORIZED),
    STATION_ALREADY_EXISTS("STATION_ALREADY_EXISTS", "A station with this code already exists", HttpStatus.BAD_REQUEST),
    TRAIN_ALREADY_EXISTS("TRAIN_ALREADY_EXISTS", "A train with this code already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_AUTHORIZED("USER_NOT_AUTHORIZED", "User is not authorized to perform this action", HttpStatus.FORBIDDEN),
    TRAIN_NOT_EXISTS("TRAIN_NOT_EXISTS", "Train does not exist", HttpStatus.NOT_FOUND),
    STATION_NOT_EXISTS("STATION_NOT_EXISTS", "Station does not exist", HttpStatus.NOT_FOUND),
    STOP_ALREADY_EXISTS("STOP_ALREADY_EXISTS", "Train stop already exists", HttpStatus.BAD_REQUEST),
    STOP_NOT_EXISTS("STOP_NOT_EXISTS", "Train stop does not exist", HttpStatus.NOT_FOUND),
    SEAT_ALREADY_EXISTS("SEAT_ALREADY_EXISTS", "Seat already exists", HttpStatus.BAD_REQUEST),
    SEAT_NOT_EXISTS("SEAT_NOT_EXISTS", "Seat does not exist", HttpStatus.NOT_FOUND),
    BOOKING_NOT_FOUND("BOOKING_NOT_FOUND", "Booking not found", HttpStatus.NOT_FOUND),
    SEAT_NOT_AVAILABLE("SEAT_NOT_AVAILABLE", "No seats available for the selected train and date", HttpStatus.BAD_REQUEST),
    INVALID_ROUTE("INVALID_ROUTE", "Source station must be before destination station in the train route", HttpStatus.BAD_REQUEST);
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
