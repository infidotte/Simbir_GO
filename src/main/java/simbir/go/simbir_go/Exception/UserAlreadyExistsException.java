package simbir.go.simbir_go.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(
                "User with username (" + message + ") already exists");
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super("User with username (" + message + ") already exists", cause);
    }
}
