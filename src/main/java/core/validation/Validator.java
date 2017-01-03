package core.validation;
import core.domain.validation.ValidationSummary;

public interface Validator<TModelToValidate> {
    ValidationSummary validate(TModelToValidate model);
}
