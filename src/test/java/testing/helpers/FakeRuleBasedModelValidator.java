package testing.helpers;

import core.validation.RuleBasedValidator;
import core.validation.ValidationRule;

import java.util.List;

public class FakeRuleBasedModelValidator extends RuleBasedValidator<FakeModel> {
    public FakeRuleBasedModelValidator(List<ValidationRule<FakeModel>> validationRules) {
        super(validationRules);
    }
}
