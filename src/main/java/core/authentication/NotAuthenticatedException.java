package core.authentication;

import core.domain.CarShowroomApplicationException;

public final class NotAuthenticatedException
        extends CarShowroomApplicationException {

    public NotAuthenticatedException() {
        super(buildMessage());
    }

    private static String buildMessage() {
        return "No user is AUTHENTICATED. In order to proceed please login in to the system first!";
    }
}
