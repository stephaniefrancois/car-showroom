package data.sql;

import app.RootLogger;
import common.SettingsStore;
import core.authentication.model.UserProfile;
import core.customer.model.Customer;
import core.deal.model.*;
import core.stock.model.CarDetails;
import data.CarDealRepository;
import data.CarRepository;
import data.ConnectionStringProvider;
import data.CustomerRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MsSqlCarDealRepository extends MsSqlRepository implements CarDealRepository {
    private static final Logger log = RootLogger.get();

    private final String GET_TOP_50_DEALS = "SELECT TOP 50 cd.CarDealId, cd.DealDate, cd.DealAmount, c.Make, c.Model, c.Year, " +
            "cu.FirstName, cu.LastName, po.DurationInMonths " +
            "FROM dbo.CarDeals cd " +
            "INNER JOIN dbo.Cars c ON c.CarId = cd.CarId " +
            "INNER JOIN dbo.Customers cu ON cu.CustomerId = cd.CustomerId " +
            "INNER JOIN dbo.PaymentOptions po ON po.CarDealId = cd.CarDealId " +
            "WHERE cd.IsDeleted = 0";
    private final String GET_CAR_DEAL_BY_ID = "SELECT cd.*, p.FirstName, p.LastName, po.DurationInMonths, po.FirstPaymentDate, po.Deposit " +
            "FROM dbo.CarDeals cd " +
            "INNER JOIN dbo.PaymentOptions po ON po.CarDealId = cd.CarDealId " +
            "INNER JOIN dbo.UserProfiles p ON p.UserProfileId = cd.SalesRepresentativeId " +
            "WHERE cd.CarDealId = ?";
    private final String GET_CAR_DEAL_PAYMENTS = "SELECT PaymentDate, Amount " +
            "FROM dbo.Payments " +
            "WHERE CarDealId = ?";
    private final String SEARCH_FOR_CAR_DEALS = "SELECT TOP 50 cd.CarDealId, cd.DealDate, cd.DealAmount, c.Make, c.Model, c.Year, " +
            "cu.FirstName, cu.LastName, po.DurationInMonths " +
            "FROM dbo.CarDeals cd " +
            "INNER JOIN dbo.Cars c ON c.CarId = cd.CarId " +
            "INNER JOIN dbo.Customers cu ON cu.CustomerId = cd.CustomerId " +
            "INNER JOIN dbo.PaymentOptions po ON po.CarDealId = cd.CarDealId " +
            "WHERE cd.IsDeleted = 0" +
            "AND (cu.FirstName LIKE '%' + ? + '%' " +
            "OR cu.LastName LIKE '%' + ? + '%' " +
            "OR cd.DealDate LIKE '%' + ? + '%' " +
            "OR c.Make LIKE '%' + ? + '%' " +
            "OR c.Model LIKE '%' + ? + '%' " +
            "OR c.Year LIKE '%' + ? + '%')";

    private final String INSERT_CAR_DEAL = "INSERT INTO dbo.CarDeals (CarId, CustomerId, DealDate, SalesRepresentativeId, DealAmount) " +
            "VALUES (?, ?, ?, ?, ?)";
    private final String INSERT_PAYMENT_OPTIONS = "INSERT INTO dbo.PaymentOptions (CarDealId ,DurationInMonths ,FirstPaymentDate ,Deposit) " +
            "VALUES (?, ?, ?, ?)";
    private final String UPDATE_PAYMENT_OPTIONS = "UPDATE dbo.PaymentOptions " +
            "SET DurationInMonths = ?, FirstPaymentDate = ?, Deposit =? " +
            "WHERE CarDealId = ?";
    private final String INSERT_PAYMENT = "INSERT INTO dbo.Payments(CarDealId, PaymentDate, Amount) VALUES (?, ?, ?)";
    private final String DELETE_CAR_DEAL_PAYMENTS = "DELETE FROM dbo.Payments " +
            "WHERE CarDealId = ?";

    private final String UPDATE_CAR_DEAL = "UPDATE dbo.CarDeals SET CarId = ? ,CustomerId = ? ,DealAmount = ? WHERE CarDealId = ?";
    private final String DELETE_CAR_DEAL = "UPDATE c SET c.IsDeleted = 1 " +
            "FROM dbo.CarDeals c " +
            "WHERE c.CarDealId = ?";
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public MsSqlCarDealRepository(ConnectionStringProvider connectionStringProvider,
                                  SettingsStore settings,
                                  CarRepository carRepository,
                                  CustomerRepository customerRepository) {
        super(connectionStringProvider, settings);
        Objects.requireNonNull(carRepository);
        Objects.requireNonNull(customerRepository);
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public List<CarDeal> getDeals() {
        List<CarDeal> deals = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_TOP_50_DEALS)
        ) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("CarDealId");
                LocalDate dealDate = rs.getDate("DealDate").toLocalDate();
                BigDecimal amount = rs.getBigDecimal("DealAmount");
                String make = rs.getString("Make");
                String model = rs.getString("Model");
                int year = rs.getInt("Year");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                int durationInMonths = rs.getInt("DurationInMonths");
                String carTitle = String.format("%s %s (%d)", make, model, year);
                String customerName = String.format("%s %s", firstName, lastName);
                CarDeal deal = new CarDeal(id, carTitle, customerName, dealDate, amount, durationInMonths);
                deals.add(deal);
                log.finer(() -> String.format("DEAL '%s' has been loaded.", deal));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> "Failed to retrieve DEALS!");
        }

        return deals;
    }

    @Override
    public CarDealDetails getDeal(int carDealId) {
        try (
                Connection connection = getConnection();
                PreparedStatement getCarDealStatement = connection.prepareStatement(GET_CAR_DEAL_BY_ID);
                PreparedStatement getPayments = connection.prepareStatement(GET_CAR_DEAL_PAYMENTS)
        ) {
            List<ScheduledPayment> payments = new ArrayList<>();
            getCarDealStatement.setInt(1, carDealId);
            ResultSet rsCarDeal = getCarDealStatement.executeQuery();
            if (rsCarDeal.next()) {
                int carId = rsCarDeal.getInt("CarId");
                int customerId = rsCarDeal.getInt("CustomerId");
                LocalDate dealDate = rsCarDeal.getDate("DealDate").toLocalDate();
                int salesRepresentativeId = rsCarDeal.getInt("SalesRepresentativeId");
                String salesRepresentativeFirstName = rsCarDeal.getString("FirstName");
                String salesRepresentativeLastName = rsCarDeal.getString("LastName");
                BigDecimal amount = rsCarDeal.getBigDecimal("DealAmount");
                int durationInMonths = rsCarDeal.getInt("DurationInMonths");
                LocalDate firstPaymentDate = rsCarDeal.getDate("FirstPaymentDate").toLocalDate();
                int deposit = rsCarDeal.getInt("Deposit");

                getPayments.setInt(1, carDealId);
                ResultSet rsPayments = getPayments.executeQuery();
                while (rsPayments.next()) {
                    LocalDate paymentDate = rsPayments.getDate("PaymentDate").toLocalDate();
                    BigDecimal paymentAmount = rsPayments.getBigDecimal("Amount");
                    payments.add(new ScheduledPayment(paymentAmount, paymentDate));
                }

                if (payments.isEmpty()) {
                    log.warning(() -> String.format("CAR DEAL with id '%d' has NO payments! This is not a valid state.!", carDealId));
                }

                Customer customer = this.customerRepository.getCustomer(customerId);
                CarDetails car = this.carRepository.getCar(carId);
                UserProfile user = new UserProfile(salesRepresentativeFirstName, salesRepresentativeLastName);
                SalesRepresentative salesMan = new SalesRepresentative(salesRepresentativeId, user);
                PaymentOptions paymentOptions = new PaymentOptions(durationInMonths, firstPaymentDate, deposit);
                PaymentSchedule schedule = new PaymentSchedule(amount, payments);
                CarDealDetails deal = new CarDealDetails(carDealId, car, customer, dealDate, salesMan, paymentOptions, schedule);
                log.finer(() -> String.format("CAR DEAL '%s' has been loaded.", customer));
                return deal;
            } else {
                log.warning(() -> String.format("CAR DEAL with id '%d' was not found!", carDealId));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> String.format("Failed to retrieve CAR DEAL with '%d'!", carDealId));
        }

        return null;
    }

    @Override
    public CarDealDetails saveDeal(CarDealDetails deal) {
        if (deal.getId() == 0) {
            try (
                    Connection connection = getConnection();
                    PreparedStatement insertDealStatement = connection.prepareStatement(INSERT_CAR_DEAL, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement insertPaymentOptionsStatement = connection.prepareStatement(INSERT_PAYMENT_OPTIONS);
                    PreparedStatement insertPaymentStatement = connection.prepareStatement(INSERT_PAYMENT)
            ) {
                connection.setAutoCommit(false);
                insertDealStatement.setInt(1, deal.getCar().getId());
                insertDealStatement.setInt(2, deal.getCustomer().getId());
                insertDealStatement.setDate(3, Date.valueOf(deal.getDealDate()));
                insertDealStatement.setInt(4, deal.getSalesRepresentative().getSalesRepresentativeId());
                insertDealStatement.setBigDecimal(5, deal.getPaymentSchedule().getTotalAmountToPay());
                int affectedRows = insertDealStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Failed to create CAR DEAL due to SQL error!");
                }

                try (ResultSet generatedKeys = insertDealStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int dealId = generatedKeys.getInt(1);

                        insertPaymentOptionsStatement.setInt(1, dealId);
                        insertPaymentOptionsStatement.setInt(2, deal.getPaymentOptions().getDurationInMonths());
                        insertPaymentOptionsStatement.setDate(3, Date.valueOf(deal.getPaymentOptions().getFirstPaymentDay()));
                        insertPaymentOptionsStatement.setInt(4, deal.getPaymentOptions().getDeposit());
                        insertPaymentOptionsStatement.executeUpdate();

                        if (deal.getPaymentSchedule().getScheduledPayments().size() > 0) {
                            for (ScheduledPayment payment : deal.getPaymentSchedule().getScheduledPayments()) {
                                insertPaymentStatement.setInt(1, dealId);
                                insertPaymentStatement.setDate(2, Date.valueOf(payment.getPaymentDate()));
                                insertPaymentStatement.setBigDecimal(3, payment.getAmount());
                                insertPaymentStatement.executeUpdate();
                            }
                        }

                        if (dealId > 0) {
                            connection.commit();
                            return new CarDealDetails(dealId,
                                    deal.getCar(),
                                    deal.getCustomer(),
                                    deal.getDealDate(),
                                    deal.getSalesRepresentative(),
                                    deal.getPaymentOptions(),
                                    deal.getPaymentSchedule());
                        } else {
                            connection.rollback();
                        }
                    } else {
                        throw new SQLException("Creating customer failed, no ID obtained.");
                    }
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e, () -> "Failed to CREATE new CUSTOMER!");
            }
        } else {
            try (
                    Connection connection = getConnection();
                    PreparedStatement updateCarDealStatement = connection.prepareStatement(UPDATE_CAR_DEAL);
                    PreparedStatement updatePaymentOptionsStatement = connection.prepareStatement(UPDATE_PAYMENT_OPTIONS);
                    PreparedStatement deletePaymentsStatement = connection.prepareStatement(DELETE_CAR_DEAL_PAYMENTS);
                    PreparedStatement insertPaymentStatement = connection.prepareStatement(INSERT_PAYMENT)
            ) {
                connection.setAutoCommit(false);
                updateCarDealStatement.setInt(1, deal.getCar().getId());
                updateCarDealStatement.setInt(2, deal.getCustomer().getId());
                updateCarDealStatement.setBigDecimal(3, deal.getPaymentSchedule().getTotalAmountToPay());
                updateCarDealStatement.setInt(4, deal.getId());
                int affectedRows = updateCarDealStatement.executeUpdate();

                updatePaymentOptionsStatement.setInt(1, deal.getPaymentOptions().getDurationInMonths());
                updatePaymentOptionsStatement.setDate(2, Date.valueOf(deal.getPaymentOptions().getFirstPaymentDay()));
                updatePaymentOptionsStatement.setInt(3, deal.getPaymentOptions().getDeposit());
                updatePaymentOptionsStatement.setInt(4, deal.getId());

                updatePaymentOptionsStatement.executeUpdate();

                deletePaymentsStatement.setInt(1, deal.getId());
                deletePaymentsStatement.executeUpdate();

                if (deal.getPaymentSchedule().getScheduledPayments().size() > 0) {
                    for (ScheduledPayment payment : deal.getPaymentSchedule().getScheduledPayments()) {
                        insertPaymentStatement.setInt(1, deal.getId());
                        insertPaymentStatement.setDate(2, Date.valueOf(payment.getPaymentDate()));
                        insertPaymentStatement.setBigDecimal(3, payment.getAmount());
                        insertPaymentStatement.executeUpdate();
                    }
                }

                if (affectedRows > 0) {
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new SQLException("Failed to UPDATE CAR DEAL due to SQL error!");
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e, () -> String.format("Failed to UPDATE CAR DEAL with '%d'!", deal.getId()));
            }
        }

        return deal;
    }

    @Override
    public void removeDeal(int carDealId) {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_CAR_DEAL)
        ) {
            statement.setInt(1, carDealId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to DELETE CAR DEAL due to SQL error!");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> String.format("Failed to DELETE CAR DEL with '%d'!", carDealId));
        }
    }

    @Override
    public List<CarDeal> findDeals(String searchCriteria) {
        List<CarDeal> deals = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SEARCH_FOR_CAR_DEALS)
        ) {
            statement.setString(1, searchCriteria);
            statement.setString(2, searchCriteria);
            statement.setString(3, searchCriteria);
            statement.setString(4, searchCriteria);
            statement.setString(5, searchCriteria);
            statement.setString(6, searchCriteria);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("CarDealId");
                LocalDate dealDate = rs.getDate("DealDate").toLocalDate();
                BigDecimal amount = rs.getBigDecimal("DealAmount");
                String make = rs.getString("Make");
                String model = rs.getString("Model");
                int year = rs.getInt("Year");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                int durationInMonths = rs.getInt("DurationInMonths");
                String carTitle = String.format("%s %s (%d)", make, model, year);
                String customerName = String.format("%s %s", firstName, lastName);
                CarDeal deal = new CarDeal(id, carTitle, customerName, dealDate, amount, durationInMonths);
                deals.add(deal);
                log.finer(() -> String.format("CAR DEAL '%s' has been loaded.", deal));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> "Failed to retrieve CAR DEAL!");
        }

        return deals;
    }
}
