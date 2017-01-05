package testing.helpers;

import core.domain.UserProfile;
import core.domain.car.*;
import core.domain.deal.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class TestData {
    public static class Cars {
        public static CarDetails getCar() {
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

        public static CarDetails getCar(int carId) {
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

        public static CarDetails getCar(List<CarFeature> features) {

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
    }

    public static class Customers {
        public static Customer getCustomer() {
            return new Customer(
                    "Stephanie",
                    "Francois",
                    new Date(1995, 12, 29),
                    new Date(2010, 1, 1));
        }
    }

    public static class PaymentOptionsData {
        public static PaymentOptions getPaymentOptions() {
            return new PaymentOptions(
                    24,
                    new Date(2017, 1, 1));
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

            Date dealDate = new Date(2017, 1, 1);
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
