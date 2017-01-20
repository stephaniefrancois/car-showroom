package data.inMemory;

import core.stock.model.CarFeature;
import core.stock.model.CarMetadata;
import data.CarMetadataRepository;

import java.util.Arrays;
import java.util.List;

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
        return Arrays.asList(
                new CarFeature(1 ,"Air Conditioner"),
                new CarFeature(2 ,"AM/FM radio"),
                new CarFeature(3 ,"CD/DVD Player"),
                new CarFeature(4 ,"Alloy Wheels"),
                new CarFeature(5 ,"Power Lock Doors"),
                new CarFeature(6 ,"Navigation"),
                new CarFeature(7 ,"ABS"),
                new CarFeature(8 ,"ASC"),
                new CarFeature(9 ,"LED tail-lights"),
                new CarFeature(10 ,"Power steering"),
                new CarFeature(11 ,"Power windows"),
                new CarFeature(12 ,"Air bags"),
                new CarFeature(13 ,"Sun roof"),
                new CarFeature(14 ,"Luxury Seats"),
                new CarFeature(15 ,"Premium Leather"),
                new CarFeature(16 ,"Champagne"),
                new CarFeature(17 ,"W12 Bi-Turbo engine")
        );
    }
}
