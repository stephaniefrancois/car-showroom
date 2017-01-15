package core.validation.model;

import core.CarShowroomApplicationException;

import java.util.List;

public final class ValidationException extends CarShowroomApplicationException {
    public ValidationException(List<ValidationError> errors) {
        super(buildMessage(errors));
    }

    private static String buildMessage(List<ValidationError> errors) {
        StringBuilder builder = new StringBuilder();

        builder.append("We have found '" + errors.size() + "' validation errors:");
        for (ValidationError error : errors) {
            builder.append(getNewLineSeparator());
            builder.append("    - '" + error.getFieldName() + "' -> ");
            builder.append(error.getErrorMessage());
        }

        return builder.toString();
    }

    private static String getNewLineSeparator() {
        return System.getProperty("line.separator");
    }
}
