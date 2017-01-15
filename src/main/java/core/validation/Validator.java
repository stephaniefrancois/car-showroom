package core.validation;

import core.validation.model.ValidationSummary;

public interface Validator<TModelToValidate> {
    ValidationSummary validate(TModelToValidate model);
}
