package simbir.go.simbir_go.Exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super("User with " + message + " not found");
    }
    public UserNotFoundException(String message, Throwable cause) {
        super("User with " + message + " not found", cause);
    }
}
