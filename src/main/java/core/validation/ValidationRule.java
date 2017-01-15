package core.validation;

import core.validation.model.ValidationSummary;

public interface ValidationRule<TModelToValidate> {
    ValidationSummary validate(TModelToValidate modelToValidate);
}
