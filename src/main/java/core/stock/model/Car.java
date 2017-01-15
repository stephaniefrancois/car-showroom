package core.stock.model;

import core.IHaveIdentifier;

import java.math.BigDecimal;

public class Car implements IHaveIdentifier {


    private final int carId;
    private final String make;
    private final String model;
    private final int year;
    private final Condition condition;
    private final BigDecimal price;

    public Car(int carId,
               String make,
               String model,
               int year,
               Condition condition,
               BigDecimal price) {

        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.condition = condition;
        this.price = price;
    }

    @Override
    public final int getId() {
        return carId;
    }

    public final String getMake() {
        return make;
    }

    public final String getModel() {
        return model;
    }

    public final Integer getYear() {
        return year;
    }

    public final Condition getCondition() {
        return condition;
    }

    public final BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d)", this.make, this.model, this.year);
    }
}
