package core.stock.validation;

import core.stock.model.CarDetails;
import core.validation.RuleFor;
import core.validation.ValidationRulesProvider;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class InMemoryCarValidationRulesProvider
        extends ValidationRulesProvider<CarDetails> {

    private final int minimumValueLength = 2;
    private final int maximumValueLength = 50;

    private final int minimumNumberOfSeats = 2;
    private final int maximumNumberOfSeats = 7;

    private final BigDecimal minimumPrice = new BigDecimal(1000);
    private final BigDecimal maximumPrice = new BigDecimal(1000000);

    private final int minimumMileage = 0;
    private final int maximumMileage = 10000;

    private final int minimumCarYear = 2000;
    private final int maximumCarYear = LocalDate.now().getYear();

    public InMemoryCarValidationRulesProvider() {
        addRule(RuleFor.mandatory("Body Style", CarDetails::getBodyStyle));
        addRule(RuleFor.notEmpty("Color", CarDetails::getColor));
        addRule(RuleFor.mandatory("Fuel Type", CarDetails::getFuelType));
        addRule(RuleFor.mandatory("Transmission", CarDetails::getTransmission));

        addRule(RuleFor.mandatory("Make", CarDetails::getMake));
        addRule(RuleFor.minLength(minimumValueLength, "Make", CarDetails::getMake));
        addRule(RuleFor.maxLength(maximumValueLength, "Make", CarDetails::getMake));

        addRule(RuleFor.mandatory("Model", CarDetails::getModel));
        addRule(RuleFor.minLength(minimumValueLength, "Model", CarDetails::getModel));
        addRule(RuleFor.maxLength(maximumValueLength, "Model", CarDetails::getModel));

        addRule(RuleFor.mandatory("Number of seats", CarDetails::getNumberOfSeats));
        addRule(RuleFor.minValue(minimumNumberOfSeats, "Number of seats", CarDetails::getNumberOfSeats));
        addRule(RuleFor.maxValue(maximumNumberOfSeats, "Number of seats", CarDetails::getNumberOfSeats));

        addRule(RuleFor.mandatory("Price", CarDetails::getPrice));
        addRule(RuleFor.minValue(minimumPrice, "Price", CarDetails::getPrice));
        addRule(RuleFor.maxValue(maximumPrice, "Price", CarDetails::getPrice));

        addRule(RuleFor.minValue(minimumMileage, "Mileage", CarDetails::getMileage));
        addRule(RuleFor.maxValue(maximumMileage, "Mileage", CarDetails::getMileage));

        addRule(RuleFor.mandatory("Year", CarDetails::getYear));
        addRule(RuleFor.minValue(minimumCarYear, "Year", CarDetails::getYear));
        addRule(RuleFor.maxValue(maximumCarYear, "Year", CarDetails::getYear));
    }
}
