package testing.helpers;

import core.domain.car.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class TestData {
    public static class Cars {
        public static CarDetails createCar() {
            return new CarDetails(
                    "MB",
                    "S600",
                    2017,
                    "Black",
                    new FuelType("Petrol"),
                    new BodyStyle("Sedan"),
                    new Transmission("Automatic"),
                    4,
                    new BigDecimal(500000),
                    100,
                    new ArrayList<>());
        }

        public static CarDetails createCar(int carId) {
            return new CarDetails(
                    carId,
                    "MB",
                    "S600",
                    2017,
                    "Black",
                    new FuelType("Petrol"),
                    new BodyStyle("Sedan"),
                    new Transmission("Automatic"),
                    4,
                    new BigDecimal(500000),
                    100,
                    new ArrayList<>());
        }

        public static CarDetails createCar(List<CarFeature> features) {

            return new CarDetails(
                    10,
                    "MB",
                    "S600",
                    2017,
                    "Black",
                    new FuelType("Petrol"),
                    new BodyStyle("Sedan"),
                    new Transmission("Automatic"),
                    4,
                    new BigDecimal(500000),
                    100,
                    features);
        }

        public static CarDetails createCar(List<CarFeature> features,
                                           FuelType fuelType,
                                           BodyStyle bodyStyle,
                                           Transmission transmission,
                                           BigDecimal price) {

            return new CarDetails(10,
                    "MB",
                    "S600",
                    2017,
                    "Black",
                    fuelType,
                    bodyStyle,
                    transmission,
                    4,
                    price,
                    100,
                    features);
        }
    }
}
