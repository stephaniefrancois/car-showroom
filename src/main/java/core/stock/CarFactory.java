package core.stock;

import com.sun.javaws.exceptions.InvalidArgumentException;
import core.domain.car.*;
import core.domain.car.conditions.NewCar;
import core.domain.validation.ValidationException;
import core.domain.validation.ValidationSummary;
import core.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: allows to add/remove car pictures

public final class CarFactory implements CarProperties {
    private final Validator<CarProperties> validator;
    private int carId;
    private String make;
    private String model;
    private int year;
    private String color;
    private FuelType fuelType;
    private BodyStyle bodyStyle;
    private Transmission transmission;
    private List<CarFeature> features;
    private int mileage;
    private Condition condition;
    private int numberOfSeats;
    private BigDecimal price;

    public CarFactory(Validator<CarProperties> validator) {
        this.validator = validator;
        carId = 0;
        make = "Mercedes Benz";
        model = "S600";
        year = LocalDateTime.now().getYear();
        color = "Black";
        fuelType = new FuelType("Petrol");
        bodyStyle = new BodyStyle("Sedan");
        transmission = new Transmission("Automatic");
        features = new ArrayList<>();
        mileage = 0;
        condition = new NewCar();
        numberOfSeats = 4;
        price = new BigDecimal(500000);
    }

    public CarFactory(CarDetails car, Validator<CarProperties> validator) {
        this.validator = validator;
        carId = car.getCarId();
        make = car.getMake();
        model = car.getModel();
        year = car.getYear();
        color = car.getColor();
        fuelType = car.getFuelType();
        bodyStyle = car.getBodyStyle();
        transmission = car.getTransmission();
        features = car.getFeatures();
        mileage = car.getMileage();
        condition = car.getCondition();
        numberOfSeats = car.getNumberOfSeats();
        price = car.getPrice();
    }

    @Override
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Override
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Override
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public List<CarFeature> getFeatures() {
        return features;
    }

    @Override
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public BodyStyle getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(BodyStyle bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    @Override
    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Override
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public CarProperties build() throws ValidationException {
        ValidationSummary summary = validate();
        if (summary.getIsValid() == false) {
            throw new ValidationException(summary.getValidationErrors());
        }

        CarProperties car = new CarDetails(carId,
                make,
                model,
                year,
                color,
                fuelType,
                bodyStyle,
                transmission,
                numberOfSeats,
                price,
                mileage,
                features);

        return car;
    }

    public ValidationSummary validate() {
        return validator.validate(this);
    }

    public void addCarFeature(CarFeature feature) throws InvalidArgumentException {
        if (feature == null) {
            throw new InvalidArgumentException(new String[]{"'feature' must be supplied"});
        }

        if (duplicateFeature(feature)) {
            return;
        }

        features.add(feature);
    }

    private boolean duplicateFeature(CarFeature feature) {
        return features
                .stream()
                .filter(f -> f.getDescription()
                        .equals(feature.getDescription()))
                .count() > 0;
    }

    public void removeCarFeature(CarFeature feature) throws InvalidArgumentException {
        if (feature == null) {
            throw new InvalidArgumentException(new String[]{"'feature' must be supplied"});
        }

        List<CarFeature> featuresToRemove = features
                .stream()
                .filter(f -> f.getDescription()
                        .equals(feature.getDescription())).collect(Collectors.toList());
        if (featuresToRemove.isEmpty()) {
            return;
        }

        for (CarFeature featureToRemove : featuresToRemove) {
            features.remove(featureToRemove);
        }
    }
}
