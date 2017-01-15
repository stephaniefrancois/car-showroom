package core.domain.car;

import core.domain.IHaveIdentifier;

import java.math.BigDecimal;

public interface Car extends IHaveIdentifier {
    String getMake();

    String getModel();

    Integer getYear();

    CarMetadata getFuelType();

    CarMetadata getTransmission();

    Condition getCondition();

    BigDecimal getPrice();
}
