package data;

import core.domain.car.CarFeature;

import java.util.List;

public interface CarMetadataRepository {
    List<String> getFuelTypes();

    List<String> getBodyStyles();

    List<String> getTransmissions();

    List<CarFeature> getFeatures();
}
