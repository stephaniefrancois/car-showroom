package core.domain.car;

import core.domain.car.conditions.NewCar;
import core.domain.car.conditions.UsedCar;

import java.math.BigDecimal;
import java.util.List;

public final class CarDetails implements CarProperties {
    private final int carId;
    private final String make;
    private final String model;
    private final Integer year;
    private final String color;
    private final CarMetadata fuelType;
    private final CarMetadata bodyStyle;
    private final CarMetadata transmission;
    private final List<CarFeature> features;
    private final Integer mileage;
    private final Condition condition;
    private final Integer numberOfSeats;
    private final BigDecimal price;

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
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.fuelType = fuelType;
        this.bodyStyle = bodyStyle;
        this.transmission = transmission;
        this.mileage = mileage;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        this.features = features;

        if (mileage == 0) {
            this.condition = new NewCar();
        } else {
            this.condition = new UsedCar();
        }
    }

    public List<CarFeature> getFeatures() {
        return features;
    }

    @Override
    public String getMake() {
        return make;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    @Override
    public CarMetadata getFuelType() {
        return fuelType;
    }

    public CarMetadata getBodyStyle() {
        return bodyStyle;
    }

    @Override
    public CarMetadata getTransmission() {
        return transmission;
    }

    public Integer getMileage() {
        return mileage;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public int getCarId() {
        return carId;
    }
}
