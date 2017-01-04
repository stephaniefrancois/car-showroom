package core.domain.car;

import core.domain.car.conditions.NewCar;
import core.domain.car.conditions.UsedCar;

import java.math.BigDecimal;
import java.util.List;

// TODO: use super constructors instead of initializing same members 4 times :)
public final class CarDetails implements CarProperties {
    private final int carId;
    private final String make;
    private final String model;
    private final Integer year;
    private final String color;
    private final FuelType fuelType;
    private final BodyStyle bodyStyle;
    private final Transmission transmission;
    private final List<CarFeature> features;
    private final Integer mileage;
    private final Condition condition;
    private final Integer numberOfSeats;
    private final BigDecimal price;

    public CarDetails(String make,
                      String model,
                      Integer year,
                      String color,
                      FuelType fuelType,
                      BodyStyle bodyStyle,
                      Transmission transmission,
                      int numberOfSeats,
                      BigDecimal price,
                      List<CarFeature> features) {

        this.carId = 0;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.fuelType = fuelType;
        this.bodyStyle = bodyStyle;
        this.transmission = transmission;
        this.features = features;
        this.condition = new NewCar();
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        this.mileage = 0;
    }

    public CarDetails(String make,
                      String model,
                      Integer year,
                      String color,
                      FuelType fuelType,
                      BodyStyle bodyStyle,
                      Transmission transmission,
                      int numberOfSeats,
                      BigDecimal price,
                      int mileage,
                      List<CarFeature> features) {

        this.carId = 0;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.fuelType = fuelType;
        this.bodyStyle = bodyStyle;
        this.transmission = transmission;
        this.mileage = mileage;
        this.condition = new UsedCar();
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        this.features = features;
    }

    public CarDetails(int carId,
                      String make,
                      String model,
                      Integer year,
                      String color,
                      FuelType fuelType,
                      BodyStyle bodyStyle,
                      Transmission transmission,
                      int numberOfSeats,
                      BigDecimal price,
                      List<CarFeature> features) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.fuelType = fuelType;
        this.bodyStyle = bodyStyle;
        this.transmission = transmission;
        this.features = features;
        this.condition = new NewCar();
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        this.mileage = 0;
    }

    public CarDetails(int carId,
                      String make,
                      String model,
                      Integer year,
                      String color,
                      FuelType fuelType,
                      BodyStyle bodyStyle,
                      Transmission transmission,
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
        this.condition = new UsedCar();
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        this.features = features;
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
    public FuelType getFuelType() {
        return fuelType;
    }

    public BodyStyle getBodyStyle() {
        return bodyStyle;
    }

    @Override
    public Transmission getTransmission() {
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
