package testing.helpers;

import core.validation.RuleBasedValidator;
import core.validation.ValidationRulesProvider;

public class FakeRuleBasedModelValidator extends RuleBasedValidator<FakeModel> {
    public FakeRuleBasedModelValidator(ValidationRulesProvider<FakeModel> rulesProvider) {
        super(rulesProvider);
    }
}
