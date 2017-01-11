package data;

import core.domain.car.CarFeature;
import core.domain.car.CarMetadata;

import java.util.List;

public interface CarMetadataRepository {
    List<CarMetadata> getFuelTypes();

    List<CarMetadata> getBodyStyles();

    List<CarMetadata> getTransmissions();

    List<CarFeature> getFeatures();
}
