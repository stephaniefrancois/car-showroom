package core.deal;

import core.domain.car.CarProperties;
import core.domain.deal.*;
import core.domain.validation.ValidationException;
import core.domain.validation.ValidationSummary;
import core.validation.Validator;

import java.util.Date;

public final class CarDealFactory implements CarDealProperties {

    private final PaymentScheduleCalculator paymentScheduleCalculator;
    private Validator<CarDealProperties> validator;
    private PaymentOptions paymentOptions;
    private SalesRepresentative salesRepresentative;
    private Date dealDate;
    private Customer customer;
    private CarProperties car;

    public CarDealFactory(CarDealProperties deal,
                          Validator<CarDealProperties> validator,
                          PaymentScheduleCalculator paymentScheduleCalculator) {

        this.validator = validator;
        this.paymentScheduleCalculator = paymentScheduleCalculator;
        paymentOptions = deal.getPaymentOptions();
        salesRepresentative = deal.getSalesRepresentative();
        dealDate = deal.getDealDate();
        customer = deal.getCustomer();
        car = deal.getCar();
    }

    public CarDealFactory(Validator<CarDealProperties> validator,
                          PaymentScheduleCalculator paymentScheduleCalculator) {

        this.validator = validator;
        this.paymentScheduleCalculator = paymentScheduleCalculator;
        paymentOptions = null;
        salesRepresentative = null;
        dealDate = null;
        customer = null;
        car = null;
    }

    @Override
    public PaymentOptions getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(PaymentOptions paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

    @Override
    public SalesRepresentative getSalesRepresentative() {
        return salesRepresentative;
    }

    public void setSalesRepresentative(SalesRepresentative salesRepresentative) {
        this.salesRepresentative = salesRepresentative;
    }

    @Override
    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public CarProperties getCar() {
        return car;
    }

    public void setCar(CarProperties car) {
        this.car = car;
    }

    public CarDeal build() throws ValidationException {
        ValidationSummary summary = validate();
        if (!summary.getIsValid()) {
            throw new ValidationException(summary.getValidationErrors());
        }

        PaymentSchedule paymentSchedule =
                paymentScheduleCalculator.calculatePaymentSchedule(paymentOptions);

        return new CarDeal(car,
                customer,
                dealDate,
                salesRepresentative,
                paymentOptions,
                paymentSchedule);
    }

    public ValidationSummary validate() {
        return validator.validate(this);
    }
}
