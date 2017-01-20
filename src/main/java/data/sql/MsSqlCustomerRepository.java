package data.sql;

import app.RootLogger;
import common.SettingsStore;
import core.customer.model.Customer;
import data.ConnectionStringProvider;
import data.CustomerRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MsSqlCustomerRepository extends MsSqlRepository implements CustomerRepository {
    private static final Logger log = RootLogger.get();

    private final String GET_TOP_50_CUSTOMERS = "SELECT TOP 50 CustomerId ,FirstName ,LastName ,City ,CustomerSince FROM [dbo].[Customer] WHERE IsDeleted = 0";
    private final String GET_CUSTOMER_BY_ID = "SELECT CustomerId ,FirstName ,LastName ,City ,CustomerSince FROM [dbo].[Customer] WHERE CustomerId = ?";
    private final String SEARCH_FOR_CUSTOMER = "SELECT CustomerId ,FirstName ,LastName ,City ,CustomerSince FROM [dbo].[Customer] WHERE IsDeleted = 0 AND (FirstName LIKE '%' + ? + '%' OR LastName LIKE '%' + ? + '%' OR City LIKE '%' + ? + '%')";
    private final String INSERT_CUSTOMER = "INSERT INTO dbo.Customer (FirstName ,LastName ,City ,CustomerSince) VALUES (?, ?, ?, ?)";
    private final String UPDATE_CUSTOMER = "UPDATE c SET c.FirstName = ?, c.LastName = ?, c.City = ? FROM dbo.Customer c WHERE c.CustomerId = ?";
    private final String DELETE_CUSTOMER = "UPDATE c SET c.IsDeleted = 1 FROM dbo.Customer c WHERE c.CustomerId = ?";

    public MsSqlCustomerRepository(ConnectionStringProvider connectionStringProvider, SettingsStore settings) {
        super(connectionStringProvider, settings);
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_TOP_50_CUSTOMERS)
        ) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("CustomerId");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String city = rs.getString("City");
                LocalDate customerSince = rs.getDate("CustomerSince").toLocalDate();
                Customer customer = new Customer(id, firstName, lastName, city, customerSince);
                customers.add(customer);
                log.finer(() -> String.format("Customer '%s' has been loaded.", customer));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> "Failed to retrieve CUSTOMERS!");
        }

        return customers;
    }

    @Override
    public Customer getCustomer(int customerId) {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_BY_ID)
        ) {
            statement.setInt(1, customerId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("CustomerId");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String city = rs.getString("City");
                LocalDate customerSince = rs.getDate("CustomerSince").toLocalDate();
                Customer customer = new Customer(id, firstName, lastName, city, customerSince);
                log.finer(() -> String.format("Customer '%s' has been loaded.", customer));
                return customer;
            } else {
                log.warning(() -> String.format("Customer with id '%d' was not found!", customerId));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> String.format("Failed to retrieve CUSTOMER with '%d'!", customerId));
        }

        return null;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        if (customer.getId() == 0) {
            try (
                    Connection connection = getConnection();
                    PreparedStatement statement = connection.prepareStatement(INSERT_CUSTOMER, Statement.RETURN_GENERATED_KEYS)
            ) {
                statement.setString(1, customer.getFirstName());
                statement.setString(2, customer.getLastName());
                statement.setString(3, customer.getCity());
                statement.setDate(4, Date.valueOf(customer.getCustomerSince()));
                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Failed to create customer due to SQL error!");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int customerId = generatedKeys.getInt(1);
                        return new Customer(customerId, customer.getFirstName(), customer.getLastName(), customer.getCity(), customer.getCustomerSince());
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
                    PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER)
            ) {
                statement.setString(1, customer.getFirstName());
                statement.setString(2, customer.getLastName());
                statement.setString(3, customer.getCity());
                statement.setInt(4, customer.getId());
                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Failed to UPDATE customer due to SQL error!");
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, e, () -> String.format("Failed to UPDATE CUSTOMER with '%d'!", customer.getId()));
            }
        }

        return customer;
    }

    @Override
    public void removeCustomer(int customerId) {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER)
        ) {
            statement.setInt(1, customerId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to DELETE customer due to SQL error!");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> String.format("Failed to DELETE CUSTOMER with '%d'!", customerId));
        }
    }

    @Override
    public List<Customer> findCustomers(String searchCriteria) {
        List<Customer> customers = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(SEARCH_FOR_CUSTOMER)
        ) {
            statement.setString(1, searchCriteria);
            statement.setString(2, searchCriteria);
            statement.setString(3, searchCriteria);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("CustomerId");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String city = rs.getString("City");
                LocalDate customerSince = rs.getDate("CustomerSince").toLocalDate();
                Customer customer = new Customer(id, firstName, lastName, city, customerSince);
                customers.add(customer);
                log.finer(() -> String.format("Customer '%s' has been loaded.", customer));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> "Failed to retrieve CUSTOMERS!");
        }

        return customers;
    }
}
