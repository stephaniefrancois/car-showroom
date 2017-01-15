package core.deal.model;

import core.IHaveIdentifier;
import core.customer.model.Customer;
import core.stock.model.CarDetails;

import java.time.LocalDate;

// TODO: add deal id and renamed this to car deal details
// TODO: extract light weight car deal

public final class CarDealDetails implements IHaveIdentifier {
    private final int carDealId;
    private final CarDetails car;
    private final Customer customer;
    private final LocalDate dealDate;
    private final SalesRepresentative salesMan;
    private final PaymentOptions paymentOptions;
    private final PaymentSchedule paymentSchedule;

    public CarDealDetails(int carDealId,
                          CarDetails car,
                          Customer customer,
                          LocalDate dealDate,
                          SalesRepresentative salesMan,
                          PaymentOptions paymentOptions,
                          PaymentSchedule paymentSchedule) {
        this.carDealId = carDealId;
        this.car = car;
        this.customer = customer;
        this.dealDate = dealDate;
        this.salesMan = salesMan;
        this.paymentOptions = paymentOptions;
        this.paymentSchedule = paymentSchedule;
    }

    @Override
    public int getId() {
        return carDealId;
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

    public CarDetails getCar() {
        return car;
    }
}
