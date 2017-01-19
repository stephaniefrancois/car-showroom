package core.deal.validation;

import core.deal.model.CarDealDetails;
import core.validation.RuleFor;
import core.validation.ValidationRulesProvider;

import java.time.LocalDate;

public final class InMemoryCarDealValidationRulesProvider
        extends ValidationRulesProvider<CarDealDetails> {

    public InMemoryCarDealValidationRulesProvider() {

        addRule(RuleFor.mandatory("Car", CarDealDetails::getCar));
        addRule(RuleFor.mandatory("Customer", CarDealDetails::getCustomer));
        addRule(RuleFor.mandatory("Payment Options", CarDealDetails::getPaymentOptions));
        addRule(RuleFor.mandatory("Sales Representative", CarDealDetails::getSalesRepresentative));
        addRule(RuleFor.mandatory("Deal Date", CarDealDetails::getDealDate));
        //addRule(RuleFor.earliestAllowedDate(getToday(), "Deal Date", CarDealDetails::getDealDate));
        //addRule(RuleFor.latestAllowedDate(getOneMonthFromToday(), "Deal Date", CarDealDetails::getDealDate));
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
