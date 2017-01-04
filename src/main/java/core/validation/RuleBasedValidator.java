package core.validation;

import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class RuleBasedValidator<TModelToValidate>
        implements Validator<TModelToValidate> {
    private final List<ValidationRule<TModelToValidate>> rules;

    public RuleBasedValidator(List<ValidationRule<TModelToValidate>> rules) {
        Objects.requireNonNull(rules,
                "'rules' must be supplied!");

        this.rules = rules;
    }

    @Override
    public ValidationSummary validate(TModelToValidate car) {
        List<ValidationSummary> failedRuleSummaries =
                rules.stream()
                        .map(rule -> rule.validate(car))
                        .filter(this::hasFailedValidation)
                        .collect(Collectors.toList());

        if (validationPassed(failedRuleSummaries)) {
            return new ValidationSummary();
        }

        List<ValidationError> allErrors = collectAllValidationErrors(failedRuleSummaries);
        return new ValidationSummary(allErrors);
    }

    private boolean hasFailedValidation(ValidationSummary validationSummary) {
        return !validationSummary.getIsValid();
    }

    private boolean validationPassed(List<ValidationSummary> failedRuleSummaries) {
        return failedRuleSummaries.isEmpty();
    }

    private List<ValidationError> collectAllValidationErrors(List<ValidationSummary> failedRuleSummaries) {
        return failedRuleSummaries.stream()
                .map(ValidationSummary::getValidationErrors)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
