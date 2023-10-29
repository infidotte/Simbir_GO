package simbir.go.simbir_go.Exception;

public class TransportNotFoundException extends Exception{
    public TransportNotFoundException(String message) {
        super("transport with " + message + " not found");
    }
    public TransportNotFoundException(String message, Throwable cause) {
        super("Transport with " + message + " not found", cause);
    }
}
