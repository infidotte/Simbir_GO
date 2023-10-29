package simbir.go.simbir_go.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice(basePackages = "simbir.go.simbir_go.Controller")
public class ExceptionController {
    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        return new ResponseEntity<>(
                new ExceptionResponse(
                        e.getMessage(),
                        e,
                        status,
                        ZonedDateTime.now())
                , status);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(
                new ExceptionResponse(
                        e.getMessage(),
                        e,
                        status,
                        ZonedDateTime.now())
                , status);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(
                new ExceptionResponse(
                        e.getMessage(),
                        e,
                        status,
                        ZonedDateTime.now())
                , status);
    }

    @ExceptionHandler(value = {TransportNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleTransportNotFoundException(TransportNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(
                new ExceptionResponse(
                        e.getMessage(),
                        e,
                        status,
                        ZonedDateTime.now())
                , status);
    }

    @ExceptionHandler(value = {RentNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleRentNotFoundException(RentNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(
                new ExceptionResponse(
                        e.getMessage(),
                        e,
                        status,
                        ZonedDateTime.now())
                , status);
    }
    @ExceptionHandler(value = {MethodNotAllowedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ExceptionResponse> handleMethodNotAllowedException(MethodNotAllowedException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(
                new ExceptionResponse(
                        e.getMessage(),
                        e,
                        status,
                        ZonedDateTime.now())
                , status);
    }
}
