package core.validation;

import common.StringExtensions;
import core.validation.model.ValidationError;
import core.validation.model.ValidationSummary;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class TreeLikeValidationErrorsFormatter implements ValidationErrorsFormatter {
    public String format(ValidationSummary validationSummary) {
        Objects.requireNonNull(validationSummary);

        if (validationSummary.getIsValid()) {
            return "No validation errors found.";
        }

        return formatErrorMessages(validationSummary);
    }

    private String formatErrorMessages(ValidationSummary validationSummary) {
        StringBuilder builder = new StringBuilder();
        List<ValidationError> errors = validationSummary.getValidationErrors();
        List<String> invalidFields = getInvalidFields(errors);

        for (String invalidField : invalidFields) {
            if (builder.length() > 0) {
                builder.append(StringExtensions.NewLineSeparator);
                builder.append(StringExtensions.NewLineSeparator);
            }

            builder.append(String.format("%s:%s", invalidField, StringExtensions.NewLineSeparator));
            builder = formatErrorMessagesForField(builder, errors, invalidField);
        }

        return builder.toString();
    }

    private List<String> getInvalidFields(List<ValidationError> errors) {
        return errors
                .stream()
                .map(e -> e.getFieldName())
                .distinct().collect(Collectors.toList());
    }

    private StringBuilder formatErrorMessagesForField(StringBuilder builder, List<ValidationError> errors, String invalidField) {
        List<String> fieldErrors = getFieldErrors(errors, invalidField);
        for (String errorMessage : fieldErrors) {
            builder.append(String.format("  - %s", errorMessage));
            if (weHaveMoreErrors(fieldErrors, errorMessage)) {
                builder.append(StringExtensions.NewLineSeparator);
            }
        }

        return builder;
    }

    private boolean weHaveMoreErrors(List<String> fieldErrors, String errorMessage) {
        return fieldErrors.indexOf(errorMessage) < fieldErrors.size() - 1;
    }

    private List<String> getFieldErrors(List<ValidationError> errors, String field) {
        return errors
                .stream()
                .filter(e -> e.getFieldName() == field)
                .map(e -> e.getErrorMessage())
                .collect(Collectors.toList());
    }
}
