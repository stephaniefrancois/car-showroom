package core;

public abstract class CarShowroomApplicationException extends Exception {
    public CarShowroomApplicationException(String message) {
        super(message);
    }

    public CarShowroomApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
