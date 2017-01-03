package core.validation;

import core.domain.validation.ValidationSummary;

public interface ValidationRule<TModelToValidate> {
    ValidationSummary validate(TModelToValidate modelToValidate);
}
