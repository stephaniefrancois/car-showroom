package data;

import core.domain.car.CarFeature;
import core.domain.car.CarMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class InMemoryCarMetadataRepository implements CarMetadataRepository {
    @Override
    public List<CarMetadata> getFuelTypes() {
        return Arrays.asList(
                new CarMetadata(1, "Petrol"),
                new CarMetadata(2, "Diesel"),
                new CarMetadata(3, "Gas"),
                new CarMetadata(4, "Electric"));
    }

    @Override
    public List<CarMetadata> getBodyStyles() {
        return Arrays.asList(
                new CarMetadata(1, "Sedan"),
                new CarMetadata(2, "Hatchback"),
                new CarMetadata(3, "Coupe"),
                new CarMetadata(4, "Pickup"),
                new CarMetadata(5, "SUV"),
                new CarMetadata(6, "Sports"),
                new CarMetadata(7, "VAN"));
    }

    @Override
    public List<CarMetadata> getTransmissions() {
        return Arrays.asList(
                new CarMetadata(1, "Manual"),
                new CarMetadata(2, "Automatic"));
    }

    @Override
    public List<CarFeature> getFeatures() {
        List<String> features = Arrays.asList(
                "Air Conditioner",
                "AM/FM radio",
                "CD/DVD Player",
                "Alloy Wheels",
                "Power Lock Doors",
                "Navigation",
                "ABS",
                "ASC",
                "LED tail-lights",
                "Power steering",
                "Power windows",
                "Air bags",
                "Sun roof",
                "Luxury Seats"
        );

        return features
                .stream()
                .map(s -> new CarFeature(s))
                .collect(Collectors.toList());
    }
}
