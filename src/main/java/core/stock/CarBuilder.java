package core.stock;

import core.IHaveIdentifier;
import core.ItemBuilder;
import core.stock.model.CarDetails;
import core.stock.model.CarFeature;
import core.stock.model.CarMetadata;
import core.stock.model.Condition;
import core.stock.model.conditions.NewCar;
import core.validation.Validator;
import core.validation.model.ValidationException;
import core.validation.model.ValidationSummary;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: allows to add/remove car pictures

public final class CarBuilder implements IHaveIdentifier, ItemBuilder<CarDetails> {
    private final Validator<CarDetails> validator;
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

    public CarBuilder(Validator<CarDetails> validator) {

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

    public CarBuilder(CarDetails car, Validator<CarDetails> validator) {
        this(validator);

        Objects.requireNonNull(car,
                "'car' must be supplied!");

        carId = car.getId();
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
    public int getId() {
        return carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public CarMetadata getFuelType() {
        return fuelType;
    }

    public void setFuelType(CarMetadata fuelType) {
        this.fuelType = fuelType;
    }

    public CarMetadata getTransmission() {
        return transmission;
    }

    public void setTransmission(CarMetadata transmission) {
        this.transmission = transmission;
    }

    public Condition getCondition() {
        return condition;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<CarFeature> getFeatures() {
        return features;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CarMetadata getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(CarMetadata bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public CarDetails build() throws ValidationException {
        ValidationSummary summary = validate();
        if (!summary.getIsValid()) {
            throw new ValidationException(summary.getValidationErrors());
        }

        return buildCar();
    }

    private CarDetails buildCar() {
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

    @Override
    public ValidationSummary validate() {
        CarDetails car = buildCar();
        return validator.validate(car);
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
