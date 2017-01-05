package core.validation;

import java.util.ArrayList;
import java.util.List;

public abstract class ValidationRulesProvider<TModelToValidate> {
    private final List<ValidationRule<TModelToValidate>> validationRules = new ArrayList<>();

    protected void addRule(ValidationRule<TModelToValidate> rule) {
        validationRules.add(rule);
    }

    public List<ValidationRule<TModelToValidate>> getValidationRules() {
        return validationRules;
    }
}
