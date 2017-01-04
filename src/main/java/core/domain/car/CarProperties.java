package core.domain.car;

import java.util.List;

public interface CarProperties extends Car {
    List<CarFeature> getFeatures();

    String getColor();

    BodyStyle getBodyStyle();

    Integer getMileage();

    Integer getNumberOfSeats();
}
