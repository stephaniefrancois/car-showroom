package core.domain.deal;

import core.domain.car.CarProperties;

import java.util.Date;

public final class CarDeal {
    private final CarProperties car;
    private final Customer customer;
    private final Date dealDate;
    private final SalesRepresentative salesMan;
    private final PaymentOptions paymentOptions;
    private final PaymentSchedule paymentSchedule;

    public CarDeal(CarProperties car,
                   Customer customer,
                   Date dealDate,
                   SalesRepresentative salesMan,
                   PaymentOptions paymentOptions,
                   PaymentSchedule paymentSchedule) {

        this.car = car;
        this.customer = customer;
        this.dealDate = dealDate;
        this.salesMan = salesMan;
        this.paymentOptions = paymentOptions;
        this.paymentSchedule = paymentSchedule;
    }

    public PaymentSchedule getPaymentSchedule() {
        return paymentSchedule;
    }

    public PaymentOptions getPaymentOptions() {
        return paymentOptions;
    }

    public SalesRepresentative getSalesMan() {
        return salesMan;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CarProperties getCar() {
        return car;
    }
}
