package dataAccessLayer;

import core.domain.car.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class InMemoryCarRepository implements CarRepository {

    private ArrayList<CarProperties> cars = new ArrayList<>(Arrays.asList(
            new CarDetails(1, "Mercedes Benz", "S600",
                    2017,
                    "Black",
                    new FuelType("Petrol"),
                    new BodyStyle("Sedan"),
                    new Transmission("Automatic"),
                    4,
                    new BigDecimal(500000),
                    Arrays.asList(new CarFeature("Luxury Seats"),
                            new CarFeature("Premium Leather"),
                            new CarFeature("Champagne"),
                            new CarFeature("W12 Bi-Turbo engine"))
            ),
            new CarDetails(2, "Bentley", "Continetal GT",
                    2017,
                    "Gray",
                    new FuelType("Petrol"),
                    new BodyStyle("Coupe"),
                    new Transmission("Automatic"),
                    4,
                    new BigDecimal(500000),
                    Arrays.asList(new CarFeature("Luxury Seats"))
            ),
            new CarDetails(3, "BMW", "760Li",
                    2017,
                    "Electric Blue",
                    new FuelType("Petrol"),
                    new BodyStyle("Sedan"),
                    new Transmission("Automatic"),
                    4,
                    new BigDecimal(450000),
                    Arrays.asList(new CarFeature("Luxury Seats"))
            ),
            new CarDetails(4, "Audi", "80",
                    1980,
                    "Red",
                    new FuelType("Petrol"),
                    new BodyStyle("Sedan"),
                    new Transmission("Manual"),
                    4,
                    new BigDecimal(1000),
                    1000000,
                    Collections.EMPTY_LIST
            )
    ));

    @Override
    public List<Car> getCars() {
        return cars.stream().map(c -> c).collect(Collectors.toList());
    }

    @Override
    public CarProperties getCar(int carId) {
        List<CarProperties> filteredCars =
                cars.stream()
                        .filter(c -> c.getCarId() == carId)
                        .collect(Collectors.toList());

        if (filteredCars.isEmpty()) {
            return null;
        }

        return filteredCars.get(0);
    }

    @Override
    public CarProperties saveCar(CarProperties car) {
        cars.add(car);
        return car;
    }

    @Override
    public void removeCar(int carId) {
        List<CarProperties> carsToDelete = this.cars.stream().filter(c -> c.getCarId() == carId).collect(Collectors.toList());

        for (CarProperties car : carsToDelete) {
            this.cars.remove(car);
        }
    }
}
