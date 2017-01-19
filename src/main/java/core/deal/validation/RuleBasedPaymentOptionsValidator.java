package core.deal.validation;

import core.deal.model.PaymentOptions;
import core.validation.RuleBasedValidator;
import core.validation.ValidationRulesProvider;

public final class RuleBasedPaymentOptionsValidator extends RuleBasedValidator<PaymentOptions> {
    public RuleBasedPaymentOptionsValidator(ValidationRulesProvider<PaymentOptions> rulesProvider) {
        super(rulesProvider);
    }
}
