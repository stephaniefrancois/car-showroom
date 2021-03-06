package data.inMemory;

import core.stock.model.Car;
import core.stock.model.CarDetails;
import core.stock.model.CarFeature;
import core.stock.model.CarMetadata;
import data.CarRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public final class InMemoryCarRepository implements CarRepository {

    private ArrayList<CarDetails> cars = new ArrayList<>(Arrays.asList(
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
                            new CarFeature(17, "W12 Bi-Turbo engine"))
            ),
            new CarDetails(2, "Bentley", "Continental GT",
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
        return cars.stream().map(c -> new Car(
                c.getId(),
                c.getMake(),
                c.getModel(),
                c.getYear(),
                c.getCondition(),
                c.getPrice()
        )).collect(Collectors.toList());
    }

    @Override
    public CarDetails getCar(int carId) {
        List<CarDetails> filteredCars =
                cars.stream()
                        .filter(c -> c.getId() == carId)
                        .collect(Collectors.toList());

        if (filteredCars.isEmpty()) {
            return null;
        }

        return filteredCars.get(0);
    }

    @Override
    public CarDetails saveCar(CarDetails car) {
        if (car.getId() == 0) {
            car = addNewCar(car);
        } else {
            car = updateExistingCar(car);
        }

        return car;
    }

    private CarDetails addNewCar(CarDetails car) {
        Optional<Integer> highestCarIndex =
                this.cars
                        .stream()
                        .map(CarDetails::getId).max(Integer::compare);
        int newCarIndex = 1;

        if (highestCarIndex.isPresent()) {
            newCarIndex = highestCarIndex.get() + 1;
        }

        CarDetails newCar = new CarDetails(
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
        return newCar;
    }

    private CarDetails updateExistingCar(CarDetails car) {
        List<CarDetails> filteredCars =
                cars.stream()
                        .filter(c -> c.getId() == car.getId())
                        .collect(Collectors.toList());
        int indexOfUpdatedCar = this.cars.indexOf(filteredCars.get(0));
        this.cars.set(indexOfUpdatedCar, car);
        return car;
    }

    @Override
    public void removeCar(int carId) {
        List<CarDetails> carsToDelete = this.cars.stream().filter(c -> c.getId() == carId).collect(Collectors.toList());

        for (CarDetails car : carsToDelete) {
            this.cars.remove(car);
        }
    }

    @Override
    public List<Car> findCars(String searchCriteria) {
        return this.cars.stream()
                .filter(c -> c.getMake().toLowerCase().contains(searchCriteria.toLowerCase()) ||
                        c.getModel().toLowerCase().contains(searchCriteria.toLowerCase()))
                .map(c -> new Car(
                        c.getId(),
                        c.getMake(),
                        c.getModel(),
                        c.getYear(),
                        c.getCondition(),
                        c.getPrice()
                ))
                .collect(Collectors.toList());
    }
}
