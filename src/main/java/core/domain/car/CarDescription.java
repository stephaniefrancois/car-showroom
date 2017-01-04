package core.domain.car;

import java.math.BigDecimal;

public class CarDescription implements Car {


    private final int carId;
    private final String make;
    private final String model;
    private final int year;
    private final FuelType fuelType;
    private final Transmission transmission;
    private final Condition condition;
    private final BigDecimal price;

    public CarDescription(int carId,
                          String make,
                          String model,
                          int year,
                          FuelType fuelType,
                          Transmission transmission,
                          Condition condition,
                          BigDecimal price) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.condition = condition;
        this.price = price;
    }

    @Override
    public int getCarId() {
        return carId;
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

    @Override
    public FuelType getFuelType() {
        return fuelType;
    }

    @Override
    public Transmission getTransmission() {
        return transmission;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }
}