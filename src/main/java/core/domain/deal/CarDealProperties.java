package core.domain.deal;

import core.domain.car.CarProperties;

import java.util.Date;

public interface CarDealProperties {
    PaymentOptions getPaymentOptions();

    SalesRepresentative getSalesRepresentative();

    Date getDealDate();

    Customer getCustomer();

    CarProperties getCar();
}
