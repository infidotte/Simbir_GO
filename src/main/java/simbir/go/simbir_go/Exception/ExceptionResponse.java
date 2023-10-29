package simbir.go.simbir_go.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ExceptionResponse(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timeStamp) {
}
