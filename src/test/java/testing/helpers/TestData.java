package testing.helpers;

import core.domain.UserProfile;
import core.domain.car.CarDetails;
import core.domain.car.CarFeature;
import core.domain.car.CarMetadata;
import core.domain.car.CarProperties;
import core.domain.deal.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class TestData {
    public static class Cars {
        public static CarDetails getCar() {
            return new CarDetails(
                    "MB",
                    "S600",
                    2017,
                    "Black",
                    new CarMetadata(1, "Petrol"),
                    new CarMetadata(1, "Sedan"),
                    new CarMetadata(1, "Automatic"),
                    4,
                    new BigDecimal(500000),
                    100,
                    new ArrayList<>());
        }

        public static CarDetails getCar(int carId) {
            return new CarDetails(
                    carId,
                    "MB",
                    "S600",
                    2017,
                    "Black",
                    new CarMetadata(1, "Petrol"),
                    new CarMetadata(1, "Sedan"),
                    new CarMetadata(1, "Automatic"),
                    4,
                    new BigDecimal(500000),
                    100,
                    new ArrayList<>());
        }

        public static CarDetails getCar(List<CarFeature> features) {

            return new CarDetails(
                    10,
                    "MB",
                    "S600",
                    2017,
                    "Black",
                    new CarMetadata(1, "Petrol"),
                    new CarMetadata(1, "Sedan"),
                    new CarMetadata(1, "Automatic"),
                    4,
                    new BigDecimal(500000),
                    100,
                    features);
        }
    }

    public static class Customers {
        public static Customer getCustomer() {
            return new Customer(
                    "Stephanie",
                    "Francois",
                    LocalDate.of(1995, 12, 29),
                    LocalDate.of(2010, 1, 1));
        }
    }

    public static class PaymentOptionsData {
        public static PaymentOptions getPaymentOptions() {
            return new PaymentOptions(
                    24,
                    LocalDate.of(2017, 1, 1));
        }
    }

    public static class PaymentSchedules {
        public static PaymentSchedule getSchedule() {
            return new PaymentSchedule(
                    new BigDecimal(100),
                    new ArrayList<>());
        }
    }

    public static class SalesPeople {
        public static SalesRepresentative getSalesMan() {
            return new SalesRepresentative(
                    1,
                    new UserProfile("Stephanie", "Francois"));
        }
    }

    public static class Deals {
        public static CarDealProperties getDeal() {
            CarProperties car = Cars.getCar();
            Customer customer = Customers.getCustomer();
            SalesRepresentative salesMan = SalesPeople.getSalesMan();

            LocalDate dealDate = LocalDate.of(2017, 1, 1);
            PaymentOptions paymentOptions = PaymentOptionsData.getPaymentOptions();
            PaymentSchedule schedule = PaymentSchedules.getSchedule();

            return new CarDeal(car,
                    customer,
                    dealDate,
                    salesMan,
                    paymentOptions,
                    schedule
            );
        }
    }
}
