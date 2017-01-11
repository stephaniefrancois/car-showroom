package core.domain.car;

import java.math.BigDecimal;

public interface Car {
    int getCarId();

    String getMake();

    String getModel();

    Integer getYear();

    String getFuelType();

    String getTransmission();

    Condition getCondition();

    BigDecimal getPrice();
}
