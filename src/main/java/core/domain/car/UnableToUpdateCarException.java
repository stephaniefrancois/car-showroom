package core.domain.car;

import core.domain.CarShowroomApplicationException;

public final class UnableToUpdateCarException extends CarShowroomApplicationException {
    public UnableToUpdateCarException(int carId) {
        super(buildMessage(carId));
    }

    private static String buildMessage(int carId) {
        if (carId <= 0) {
            return "In order to UPDATE an existing car it must have a valid id specified!";
        }
        return String.format("Car with ID: '%s' was not found! Car must exist in order to be updated.",
                carId);
    }
}
