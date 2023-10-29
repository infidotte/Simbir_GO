package simbir.go.simbir_go.Exception;

public class RentNotFoundException extends Exception {
    public RentNotFoundException(String message) {
        super("Rent with " + message + " not found");
    }
    public RentNotFoundException(String message, Throwable cause) {
        super("Rent with " + message + " not found", cause);
    }
}
