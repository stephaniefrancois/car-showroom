package core.domain.validation;

import java.util.ArrayList;
import java.util.List;

public final class ValidationSummary {
     private final List<ValidationError> validationErrors;

    public ValidationSummary() {
        validationErrors = new ArrayList<>();
    }

    public ValidationSummary(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public boolean getIsValid() {
        return validationErrors.size() == 0;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }
}