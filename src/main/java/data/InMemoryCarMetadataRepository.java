package data;

import core.domain.car.CarFeature;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class InMemoryCarMetadataRepository implements CarMetadataRepository {
    @Override
    public List<String> getFuelTypes() {
        return Arrays.asList("Gasoline", "Diesel", "Gas", "Electric");
    }

    @Override
    public List<String> getBodyStyles() {
        return Arrays.asList("Sedan", "Hatchback", "Coupe", "Pickup", "SUV", "Sports", "VAN");
    }

    @Override
    public List<String> getTransmissions() {
        return Arrays.asList("Manual", "Automatic", "Gas", "Electric");
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
