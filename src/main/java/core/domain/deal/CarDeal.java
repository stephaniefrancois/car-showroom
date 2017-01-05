package core.domain.deal;

import core.domain.car.CarProperties;

import java.time.LocalDate;

public final class CarDeal implements CarDealProperties {
    private final CarProperties car;
    private final Customer customer;
    private final LocalDate dealDate;
    private final SalesRepresentative salesMan;
    private final PaymentOptions paymentOptions;
    private final PaymentSchedule paymentSchedule;

    public CarDeal(CarProperties car,
                   Customer customer,
                   LocalDate dealDate,
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

    public SalesRepresentative getSalesRepresentative() {
        return salesMan;
    }

    public LocalDate getDealDate() {
        return dealDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CarProperties getCar() {
        return car;
    }
}
