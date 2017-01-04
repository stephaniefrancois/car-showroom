package core.domain.car;

import java.math.BigDecimal;

public interface Car {
    int getCarId();

    String getMake();

    String getModel();

    Integer getYear();

    FuelType getFuelType();

    Transmission getTransmission();

    Condition getCondition();

    BigDecimal getPrice();
}
