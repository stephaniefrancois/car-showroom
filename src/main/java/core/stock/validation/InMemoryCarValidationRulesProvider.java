package core.stock.validation;

import core.domain.car.Car;
import core.domain.car.CarProperties;
import core.validation.RuleFor;
import core.validation.ValidationRulesProvider;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class InMemoryCarValidationRulesProvider
        extends ValidationRulesProvider<CarProperties> {

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
        addRule(RuleFor.mandatory("Body Style", CarProperties::getBodyStyle));
        addRule(RuleFor.notEmpty("Color", CarProperties::getColor));
        addRule(RuleFor.mandatory("Fuel Type", Car::getFuelType));
        addRule(RuleFor.mandatory("Transmission", Car::getTransmission));

        addRule(RuleFor.mandatory("Make", Car::getMake));
        addRule(RuleFor.minLength(minimumValueLength, "Make", Car::getMake));
        addRule(RuleFor.maxLength(maximumValueLength, "Make", Car::getMake));

        addRule(RuleFor.mandatory("Model", Car::getModel));
        addRule(RuleFor.minLength(minimumValueLength, "Model", Car::getModel));
        addRule(RuleFor.maxLength(maximumValueLength, "Model", Car::getModel));

        addRule(RuleFor.mandatory("Number of seats", CarProperties::getNumberOfSeats));
        addRule(RuleFor.minValue(minimumNumberOfSeats, "Number of seats", CarProperties::getNumberOfSeats));
        addRule(RuleFor.maxValue(maximumNumberOfSeats, "Number of seats", CarProperties::getNumberOfSeats));

        addRule(RuleFor.mandatory("Price", Car::getPrice));
        addRule(RuleFor.minValue(minimumPrice, "Price", Car::getPrice));
        addRule(RuleFor.maxValue(maximumPrice, "Price", Car::getPrice));

        addRule(RuleFor.minValue(minimumMileage, "Mileage", CarProperties::getMileage));
        addRule(RuleFor.maxValue(maximumMileage, "Mileage", CarProperties::getMileage));

        addRule(RuleFor.mandatory("Year", Car::getYear));
        addRule(RuleFor.minValue(minimumCarYear, "Year", Car::getYear));
        addRule(RuleFor.maxValue(maximumCarYear, "Year", Car::getYear));
    }
}
