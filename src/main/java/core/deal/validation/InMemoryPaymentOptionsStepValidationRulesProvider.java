package core.deal.validation;

import core.deal.model.PaymentOptions;
import core.validation.RuleFor;
import core.validation.ValidationRulesProvider;

import java.util.Arrays;

public final class InMemoryPaymentOptionsStepValidationRulesProvider
        extends ValidationRulesProvider<PaymentOptions> {

    public InMemoryPaymentOptionsStepValidationRulesProvider() {

        addRule(RuleFor.mandatory("Deposit", PaymentOptions::getDeposit));
        addRule(RuleFor.minValue(0, "Deposit", PaymentOptions::getDeposit));
        addRule(RuleFor.maxValue(1000, "Deposit", PaymentOptions::getDeposit));
        addRule(RuleFor.mandatory("First Payment", PaymentOptions::getFirstPaymentDay));
        addRule(RuleFor.mandatory("Duration In Months", PaymentOptions::getDurationInMonths));
        addRule(RuleFor.allowSetOfValues("Duration In Months",
                Arrays.asList("12", "24"),
                d -> String.valueOf(d.getDurationInMonths())));
    }
}
