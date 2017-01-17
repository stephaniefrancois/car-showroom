package app.common.validation;

import core.validation.model.ValidationSummary;

import java.util.EventObject;

public final class ValidationEventArgs extends EventObject {
    private final ValidationSummary validationSummary;

    public ValidationEventArgs(Object source, ValidationSummary validationSummary) {
        super(source);
        this.validationSummary = validationSummary;
    }

    public ValidationSummary getValidationSummary() {
        return validationSummary;
    }
}
