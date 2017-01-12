package core.validation;

import core.domain.validation.ValidationSummary;

public interface ValidationErrorsFormatter {
    String format(ValidationSummary validationSummary);
}
