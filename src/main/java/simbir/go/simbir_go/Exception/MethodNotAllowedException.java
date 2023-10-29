package simbir.go.simbir_go.Exception;

public class MethodNotAllowedException extends Exception{
    public MethodNotAllowedException(String message) {
        super(message);
    }
    public MethodNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
