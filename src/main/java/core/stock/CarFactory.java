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
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: allows to add/remove car pictures

public final class CarFactory implements CarProperties {
    private final Validator<CarProperties> validator;
    private int carId;
    private String make;
    private String model;
    private Integer year;
    private String color;
    private CarMetadata fuelType;
    private CarMetadata bodyStyle;
    private CarMetadata transmission;
    private List<CarFeature> features;
    private Integer mileage;
    private Condition condition;
    private Integer numberOfSeats;
    private BigDecimal price;

    public CarFactory(Validator<CarProperties> validator) {

        Objects.requireNonNull(validator,
                "'validator' must be supplied!");

        this.validator = validator;
        carId = 0;
        make = "";
        model = "";
        year = LocalDateTime.now().getYear();
        color = "";
        fuelType = null;
        bodyStyle = null;
        transmission = null;
        features = new ArrayList<>();
        mileage = 0;
        condition = new NewCar();
        numberOfSeats = 0;
        price = new BigDecimal(0);
    }

    public CarFactory(CarProperties car, Validator<CarProperties> validator) {
        this(validator);

        Objects.requireNonNull(car,
                "'car' must be supplied!");

        carId = car.getCarId();
        make = car.getMake();
        model = car.getModel();
        year = car.getYear();
        color = car.getColor();
        fuelType = car.getFuelType();
        bodyStyle = car.getBodyStyle();
        transmission = car.getTransmission();
        features = new ArrayList<>(car.getFeatures());
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
    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public CarMetadata getFuelType() {
        return fuelType;
    }

    public void setFuelType(CarMetadata fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public CarMetadata getTransmission() {
        return transmission;
    }

    public void setTransmission(CarMetadata transmission) {
        this.transmission = transmission;
    }

    @Override
    public Condition getCondition() {
        return condition;
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
    public CarMetadata getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(CarMetadata bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    @Override
    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Override
    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public CarProperties build() throws ValidationException {
        ValidationSummary summary = validate();
        if (!summary.getIsValid()) {
            throw new ValidationException(summary.getValidationErrors());
        }

        return new CarDetails(carId,
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
    }

    public ValidationSummary validate() {
        return validator.validate(this);
    }

    public void addCarFeature(CarFeature feature) {
        Objects.requireNonNull(feature);

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

    public void removeCarFeature(CarFeature feature) {
        Objects.requireNonNull(feature);

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

    public void removeAllFeatures() {
        this.features.clear();
    }
}
