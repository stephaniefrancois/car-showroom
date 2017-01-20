package data.sql;

import app.RootLogger;
import common.SettingsStore;
import core.stock.model.*;
import core.stock.model.conditions.NewCar;
import core.stock.model.conditions.UsedCar;
import data.CarMetadataRepository;
import data.CarRepository;
import data.ConnectionStringProvider;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class MsSqlCarRepository extends MsSqlRepository implements CarRepository {
    private static final Logger log = RootLogger.get();

    private final String GET_TOP_50_CARS = "SELECT TOP 50 [CarId], [Make], [Model], [Year], [Price], [Mileage] FROM [dbo].[Cars] c WHERE c.[IsDeleted] = 0";
    private final String GET_CAR_BY_ID = "SELECT [CarId] ,[Make] ,[Model] ,[Year] ,[Color] ,[FuelType] ,[BodyStyle] ,[Transmission] ,[NumberOfSeats] ,[Price] ,[Mileage] FROM [dbo].[Cars] WHERE [CarId] = ?";
    private final String GET_CAR_FEATURES_BY_ID = "SELECT [CarFeatureId] FROM [dbo].[CarsFeaturesMap] WHERE CarId = ?";
    private final String SEARCH_FOR_CAR = "SELECT [CarId], [Make], [Model], [Year], [Price], [Mileage] FROM [dbo].[Cars] WHERE IsDeleted = 0 AND (Model LIKE '%' + ? + '%' OR Make LIKE '%' + ? + '%' OR Year LIKE '%' + ? + '%')";
    private final String INSERT_CAR = "INSERT INTO dbo.Cars (Make, Model,  Year,  Color,  FuelType,  BodyStyle,  Transmission,  NumberOfSeats,  Price,  Mileage) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String INSERT_CAR_FEATURE = "INSERT INTO dbo.CarsFeaturesMap (CarId, CarFeatureId) VALUES  (?, ?)";
    private final String DELETE_CAR_FEATURES = "DELETE FROM dbo.CarsFeaturesMap WHERE CarId = ?";
    private final String UPDATE_CAR = "UPDATE [dbo].[Cars] SET [Make] = ?, [Model] = ?, [Year] = ?, [Color] = ?, [FuelType] = ?, [BodyStyle] = ?, [Transmission] = ?, [NumberOfSeats] = ?, [Price] = ?, [Mileage] = ? WHERE CarId = ?";
    private final String DELETE_CAR = "UPDATE c SET c.IsDeleted = 1 FROM dbo.Cars c WHERE c.CarId = ?";

    private final CarMetadataRepository carMetadataRepository;

    public MsSqlCarRepository(ConnectionStringProvider connectionStringProvider, SettingsStore settings,
                              CarMetadataRepository carMetadataRepository) {
        super(connectionStringProvider, settings);
        Objects.requireNonNull(carMetadataRepository);
        this.carMetadataRepository = carMetadataRepository;
    }

    @Override
    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_TOP_50_CARS)
        ) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("CarId");
                String make = rs.getString("Make");
                String model = rs.getString("Model");
                int year = rs.getInt("Year");
                BigDecimal price = rs.getBigDecimal("Price");
                int mileage = rs.getInt("Mileage");
                Condition condition = new NewCar();
                if (mileage > 0) {
                    condition = new UsedCar();
                }
                Car car = new Car(id, make, model, year, condition, price);
                cars.add(car);
                log.finer(() -> String.format("Car '%s' has been loaded.", car));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> "Failed to retrieve CARS!");
        }

        return cars;
    }

    @Override
    public CarDetails getCar(int carId) {
        try (
                Connection connection = getConnection();
                PreparedStatement getCarStatement = connection.prepareStatement(GET_CAR_BY_ID);
                PreparedStatement getCarFeaturesStatement = connection.prepareStatement(GET_CAR_FEATURES_BY_ID)
        ) {
            getCarStatement.setInt(1, carId);
            getCarFeaturesStatement.setInt(1, carId);
            ResultSet rs = getCarStatement.executeQuery();
            if (rs.next()) {
                List<CarFeature> features = new ArrayList<>();

                int id = rs.getInt("CarId");
                String model = rs.getString("Make");
                String make = rs.getString("Model");
                int year = rs.getInt("Year");
                String color = rs.getString("Color");
                int fuelTypeId = rs.getInt("FuelType");
                int bodyStyleId = rs.getInt("BodyStyle");
                int transmissionId = rs.getInt("Transmission");
                int numberOfSeats = rs.getInt("NumberOfSeats");
                BigDecimal price = rs.getBigDecimal("Price");
                int mileage = rs.getInt("Mileage");

                CarMetadata fuelType = this.carMetadataRepository.getFuelTypes().stream().filter(f -> f.getId() == fuelTypeId).collect(Collectors.toList()).get(0);
                CarMetadata bodyStyle = this.carMetadataRepository.getBodyStyles().stream().filter(f -> f.getId() == bodyStyleId).collect(Collectors.toList()).get(0);
                CarMetadata transmission = this.carMetadataRepository.getTransmissions().stream().filter(f -> f.getId() == transmissionId).collect(Collectors.toList()).get(0);

                ResultSet rsFeatures = getCarFeaturesStatement.executeQuery();
                while (rsFeatures.next()) {
                    int carFeatureId = rsFeatures.getInt("CarFeatureId");
                    List<CarFeature> matchingFeatures = this.carMetadataRepository.getFeatures()
                            .stream()
                            .filter(f -> f.getFeatureId() == carFeatureId)
                            .collect(Collectors.toList());
                    if (matchingFeatures.isEmpty()) {
                        log.warning(() -> String.format("Car feature with id '%d' couldn't be resolved!", carFeatureId));
                    } else {
                        features.add(matchingFeatures.get(0));
                    }
                }

                CarDetails car = new CarDetails(id, make, model, year, color, fuelType, bodyStyle, transmission, numberOfSeats, price, mileage, features);
                log.finer(() -> String.format("CAR '%s' has been loaded.", car));
                return car;
            } else {
                log.warning(() -> String.format("CAR with id '%d' was not found!", carId));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> String.format("Failed to retrieve CAR with '%d'!", carId));
        }

        return null;
    }

    @Override
    public CarDetails saveCar(CarDetails car) {
        if (car.getId() == 0) {
            try (
                    Connection connection = getConnection();
                    PreparedStatement insertStatement = connection.prepareStatement(INSERT_CAR, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement insertCarFeatureStatement = connection.prepareStatement(INSERT_CAR_FEATURE)
            ) {
                connection.setAutoCommit(false);
                setCarPropertiesForStatement(car, insertStatement);

                int affectedRows = insertStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Failed to create CAR due to SQL error!");
                }

                try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int carId = generatedKeys.getInt(1);
                        if (car.getFeatures().size() > 0) {
                            for (CarFeature feature : car.getFeatures()) {
                                insertCarFeatureStatement.setInt(1, carId);
                                insertCarFeatureStatement.setInt(2, feature.getFeatureId());
                                insertCarFeatureStatement.executeUpdate();
                            }
                        }

                        if (carId > 0) {
                            connection.commit();
                            return new CarDetails(carId,
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
                        } else {
                            connection.rollback();
                        }
                    } else {
                        throw new SQLException("Creating CAR failed, no ID obtained.");
                    }
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e, () -> "Failed to CREATE new CAR!");
            }
        } else {
            try (
                    Connection connection = getConnection();
                    PreparedStatement updateStatement = connection.prepareStatement(UPDATE_CAR);
                    PreparedStatement deleteCarFeaturesStatement = connection.prepareStatement(DELETE_CAR_FEATURES);
                    PreparedStatement insertCarFeatureStatement = connection.prepareStatement(INSERT_CAR_FEATURE)
            ) {
                connection.setAutoCommit(false);

                deleteCarFeaturesStatement.setInt(1, car.getId());
                deleteCarFeaturesStatement.executeUpdate();

                setCarPropertiesForStatement(car, updateStatement);
                updateStatement.setInt(11, car.getId());
                int affectedRows = updateStatement.executeUpdate();

                if (car.getFeatures().size() > 0) {
                    for (CarFeature feature : car.getFeatures()) {
                        insertCarFeatureStatement.setInt(1, car.getId());
                        insertCarFeatureStatement.setInt(2, feature.getFeatureId());
                        insertCarFeatureStatement.executeUpdate();
                    }
                }

                if (affectedRows > 0) {
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new SQLException("Failed to UPDATE CAR due to SQL error!");
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e, () -> String.format("Failed to UPDATE CAR with '%d'!", car.getId()));
            }
        }

        return car;
    }

    private void setCarPropertiesForStatement(CarDetails car, PreparedStatement statement) throws SQLException {
        statement.setString(1, car.getMake());
        statement.setString(2, car.getModel());
        statement.setInt(3, car.getYear());
        statement.setString(4, car.getColor());
        statement.setInt(5, car.getFuelType().getId());
        statement.setInt(6, car.getBodyStyle().getId());
        statement.setInt(7, car.getTransmission().getId());
        statement.setInt(8, car.getNumberOfSeats());
        statement.setBigDecimal(9, car.getPrice());
        statement.setInt(10, car.getMileage());
    }

    @Override
    public void removeCar(int carId) {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_CAR)
        ) {
            statement.setInt(1, carId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to DELETE CAR due to SQL error!");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> String.format("Failed to DELETE CAR with '%d'!", carId));
        }
    }

    @Override
    public List<Car> findCars(String searchCriteria) {
        List<Car> cars = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SEARCH_FOR_CAR)
        ) {
            statement.setString(1, searchCriteria);
            statement.setString(2, searchCriteria);
            statement.setString(3, searchCriteria);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("CarId");
                String make = rs.getString("Make");
                String model = rs.getString("Model");
                int year = rs.getInt("Year");
                BigDecimal price = rs.getBigDecimal("Price");
                int mileage = rs.getInt("Mileage");
                Condition condition = new NewCar();
                if (mileage > 0) {
                    condition = new UsedCar();
                }
                Car car = new Car(id, make, model, year, condition, price);
                cars.add(car);
                log.finer(() -> String.format("CAR '%s' has been loaded.", car));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> "Failed to retrieve CARS!");
        }

        return cars;
    }
}
