package core.deal.validation;

import core.domain.deal.CarDeal;
import core.validation.RuleFor;
import core.validation.ValidationRulesProvider;

import java.time.LocalDate;

public final class InMemoryCarDealValidationRulesProvider
        extends ValidationRulesProvider<CarDeal> {

    public InMemoryCarDealValidationRulesProvider() {

        addRule(RuleFor.mandatory("Car", CarDeal::getCar));
        addRule(RuleFor.mandatory("Customer", CarDeal::getCustomer));
        addRule(RuleFor.mandatory("Payment Options", CarDeal::getPaymentOptions));
        addRule(RuleFor.mandatory("Sales Representative", CarDeal::getSalesRepresentative));
        addRule(RuleFor.mandatory("Deal Date", CarDeal::getDealDate));
        addRule(RuleFor.earliestAllowedDate(getToday(), "Deal Date", CarDeal::getDealDate));
        addRule(RuleFor.latestAllowedDate(getOneMonthFromToday(), "Deal Date", CarDeal::getDealDate));
    }

    private static LocalDate getOneMonthFromToday() {
        return LocalDate.now()
                .plusMonths(1);
    }

    private LocalDate getToday() {
        return LocalDate.now()
                .atStartOfDay()
                .toLocalDate();
    }
}
