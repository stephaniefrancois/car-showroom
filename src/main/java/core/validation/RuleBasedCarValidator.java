package core.validation;

import core.domain.car.CarProperties;
import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;

import java.util.List;
import java.util.stream.Collectors;

public class RuleBasedCarValidator implements CarValidator {
    private final List<ValidationRule> rules;

    public RuleBasedCarValidator(List<ValidationRule> rules) {
        this.rules = rules;
    }

    @Override
    public ValidationSummary validate(CarProperties car) {
        List<ValidationSummary> failedRuleSummaries =
                rules.stream()
                        .map(rule -> rule.validate(car))
                        .filter(validationSummary -> hasFailedValidation(validationSummary))
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
                .map(vs -> vs.getValidationErrors())
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
