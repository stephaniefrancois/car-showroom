package core.stock.model;

import core.stock.model.conditions.NewCar;
import core.stock.model.conditions.UsedCar;

import java.math.BigDecimal;
import java.util.List;

public final class CarDetails extends Car {

    private final CarMetadata fuelType;
    private final CarMetadata transmission;
    private final String color;
    private final CarMetadata bodyStyle;
    private final List<CarFeature> features;
    private final Integer mileage;
    private final Integer numberOfSeats;

    public CarDetails(String make,
                      String model,
                      Integer year,
                      String color,
                      CarMetadata fuelType,
                      CarMetadata bodyStyle,
                      CarMetadata transmission,
                      int numberOfSeats,
                      BigDecimal price,
                      List<CarFeature> features) {
        this(0, make, model, year, color,
                fuelType, bodyStyle, transmission,
                numberOfSeats, price, 0, features);
    }

    public CarDetails(String make,
                      String model,
                      Integer year,
                      String color,
                      CarMetadata fuelType,
                      CarMetadata bodyStyle,
                      CarMetadata transmission,
                      int numberOfSeats,
                      BigDecimal price,
                      int mileage,
                      List<CarFeature> features) {
        this(0, make, model, year, color,
                fuelType, bodyStyle, transmission,
                numberOfSeats, price, mileage, features);
    }

    public CarDetails(int carId,
                      String make,
                      String model,
                      Integer year,
                      String color,
                      CarMetadata fuelType,
                      CarMetadata bodyStyle,
                      CarMetadata transmission,
                      int numberOfSeats,
                      BigDecimal price,
                      List<CarFeature> features) {
        this(carId, make, model, year, color,
                fuelType, bodyStyle, transmission,
                numberOfSeats, price, 0, features);
    }

    public CarDetails(int carId,
                      String make,
                      String model,
                      Integer year,
                      String color,
                      CarMetadata fuelType,
                      CarMetadata bodyStyle,
                      CarMetadata transmission,
                      int numberOfSeats,
                      BigDecimal price,
                      int mileage,
                      List<CarFeature> features) {
        super(carId, make, model, year, resolveCondition(mileage), price);
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.color = color;
        this.bodyStyle = bodyStyle;
        this.mileage = mileage;
        this.numberOfSeats = numberOfSeats;
        this.features = features;
    }

    private static Condition resolveCondition(int mileage) {
        if (mileage == 0) {
            return new NewCar();
        }
        return new UsedCar();

    }

    public final CarMetadata getFuelType() {
        return fuelType;
    }

    public final CarMetadata getTransmission() {
        return transmission;
    }

    public final List<CarFeature> getFeatures() {
        return features;
    }

    public final String getColor() {
        return color;
    }

    public final CarMetadata getBodyStyle() {
        return bodyStyle;
    }

    public final Integer getMileage() {
        return mileage;
    }

    public final Integer getNumberOfSeats() {
        return numberOfSeats;
    }
}
