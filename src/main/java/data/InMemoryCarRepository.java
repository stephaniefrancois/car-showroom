package data;

import core.domain.car.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public final class InMemoryCarRepository implements CarRepository {

    private ArrayList<CarProperties> cars = new ArrayList<>(Arrays.asList(
            new CarDetails(1, "Mercedes Benz", "S600",
                    2017,
                    "Black",
                    new CarMetadata(1, "Petrol"),
                    new CarMetadata(1, "Sedan"),
                    new CarMetadata(2, "Automatic"),
                    4,
                    new BigDecimal(500000),
                    Arrays.asList(new CarFeature(14, "Luxury Seats"),
                            new CarFeature(15, "Premium Leather"),
                            new CarFeature(16, "Champagne"),
                            new CarFeature(17,"W12 Bi-Turbo engine"))
            ),
            new CarDetails(2, "Bentley", "Continetal GT",
                    2017,
                    "Gray",
                    new CarMetadata(1, "Petrol"),
                    new CarMetadata(3, "Coupe"),
                    new CarMetadata(2, "Automatic"),
                    4,
                    new BigDecimal(500000),
                    Collections.singletonList(new CarFeature(14, "Luxury Seats"))
            ),
            new CarDetails(3, "BMW", "760Li",
                    2017,
                    "Electric Blue",
                    new CarMetadata(1, "Petrol"),
                    new CarMetadata(1, "Sedan"),
                    new CarMetadata(2, "Automatic"),
                    4,
                    new BigDecimal(450000),
                    Collections.singletonList(new CarFeature(14, "Luxury Seats"))
            ),
            new CarDetails(4, "Audi", "80",
                    1980,
                    "Red",
                    new CarMetadata(2, "Diesel"),
                    new CarMetadata(2, "Hatchback"),
                    new CarMetadata(1, "Manual"),
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
        if (car.getCarId() == 0) {
            addNewCar(car);
        } else {
            updateExistingCar(car);
        }

        return car;
    }

    private void addNewCar(CarProperties car) {
        Optional<Integer> highestCarIndex =
                this.cars
                        .stream()
                        .map(Car::getCarId).max(Integer::compare);
        int newCarIndex = 1;

        if (highestCarIndex.isPresent()) {
            newCarIndex = highestCarIndex.get() + 1;
        }

        CarProperties newCar = new CarDetails(
                newCarIndex,
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getColor(),
                car.getFuelType(),
                car.getBodyStyle(),
                car.getTransmission(),
                car.getNumberOfSeats(),
                car.getPrice(),
                car.getMileage(),
                car.getFeatures());

        this.cars.add(newCar);
    }

    private void updateExistingCar(CarProperties car) {
        List<CarProperties> filteredCars =
                cars.stream()
                        .filter(c -> c.getCarId() == car.getCarId())
                        .collect(Collectors.toList());
        int indexOfUpdatedCar = this.cars.indexOf(filteredCars.get(0));
        this.cars.set(indexOfUpdatedCar, car);
    }

    @Override
    public void removeCar(int carId) {
        List<CarProperties> carsToDelete = this.cars.stream().filter(c -> c.getCarId() == carId).collect(Collectors.toList());

        for (CarProperties car : carsToDelete) {
            this.cars.remove(car);
        }
    }

    @Override
    public List<Car> find(String searchCriteria) {
        return this.cars.stream()
                .filter(c -> c.getMake().toLowerCase().contains(searchCriteria.toLowerCase()) ||
                        c.getModel().toLowerCase().contains(searchCriteria.toLowerCase()))
                .collect(Collectors.toList());
    }
}
