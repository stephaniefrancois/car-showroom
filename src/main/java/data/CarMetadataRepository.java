package data;

import core.stock.model.CarFeature;
import core.stock.model.CarMetadata;

import java.util.List;

public interface CarMetadataRepository {
    List<CarMetadata> getFuelTypes();

    List<CarMetadata> getBodyStyles();

    List<CarMetadata> getTransmissions();

    List<CarFeature> getFeatures();
}
