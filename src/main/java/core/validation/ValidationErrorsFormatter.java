package core.validation;

import core.validation.model.ValidationSummary;

public interface ValidationErrorsFormatter {
    String format(ValidationSummary validationSummary);
}
