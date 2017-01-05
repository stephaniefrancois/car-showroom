package core.domain.deal;

import core.domain.car.CarProperties;

import java.time.LocalDate;

public interface CarDealProperties {
    PaymentOptions getPaymentOptions();

    SalesRepresentative getSalesRepresentative();

    LocalDate getDealDate();

    Customer getCustomer();

    CarProperties getCar();
}
