package core.deal;

import core.ItemFactory;
import core.customer.model.Customer;
import core.deal.model.CarDealDetails;
import core.deal.model.PaymentOptions;
import core.deal.model.PaymentSchedule;
import core.deal.model.SalesRepresentative;
import core.stock.model.CarDetails;
import core.validation.Validator;
import core.validation.model.ValidationException;
import core.validation.model.ValidationSummary;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public final class CarDealFactory implements ItemFactory<CarDealDetails> {

    private final PaymentScheduleCalculator paymentScheduleCalculator;
    private Validator<CarDealDetails> validator;
    private int carDealId;
    private PaymentOptions paymentOptions;
    private SalesRepresentative salesRepresentative;
    private LocalDate dealDate;
    private Customer customer;
    private CarDetails car;

    public CarDealFactory(CarDealDetails deal,
                          Validator<CarDealDetails> validator,
                          PaymentScheduleCalculator paymentScheduleCalculator) {
        this(validator, paymentScheduleCalculator);

        Objects.requireNonNull(deal,
                "'deal' must be supplied!");

        carDealId = deal.getId();
        paymentOptions = deal.getPaymentOptions();
        salesRepresentative = deal.getSalesRepresentative();
        dealDate = deal.getDealDate();
        customer = deal.getCustomer();
        car = deal.getCar();
    }

    public CarDealFactory(Validator<CarDealDetails> validator,
                          PaymentScheduleCalculator paymentScheduleCalculator) {

        Objects.requireNonNull(validator,
                "'validator' must be supplied!");
        Objects.requireNonNull(paymentScheduleCalculator,
                "'paymentScheduleCalculator' must be supplied!");

        this.validator = validator;
        this.paymentScheduleCalculator = paymentScheduleCalculator;
        carDealId = 0;
        paymentOptions = null;
        salesRepresentative = null;
        dealDate = null;
        customer = null;
        car = null;
    }

    public int getId() {
        return carDealId;
    }

    public PaymentOptions getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(PaymentOptions paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

    public SalesRepresentative getSalesRepresentative() {
        return salesRepresentative;
    }

    public void setSalesRepresentative(SalesRepresentative salesRepresentative) {
        this.salesRepresentative = salesRepresentative;
    }

    public LocalDate getDealDate() {
        return dealDate;
    }

    public void setDealDate(LocalDate dealDate) {
        this.dealDate = dealDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CarDetails getCar() {
        return car;
    }

    public void setCar(CarDetails car) {
        this.car = car;
    }

    public CarDealDetails build() throws ValidationException {
        ValidationSummary summary = validate();
        if (!summary.getIsValid()) {
            throw new ValidationException(summary.getValidationErrors());
        }

        return buildCarDeal();
    }

    private CarDealDetails buildCarDeal() {
        BigDecimal totalAmountToPay = new BigDecimal(0);

        if (car != null) {
            totalAmountToPay = car.getPrice();
        }

        PaymentSchedule paymentSchedule =
                paymentScheduleCalculator.calculatePaymentSchedule(
                        totalAmountToPay,
                        paymentOptions);

        return new CarDealDetails(
                carDealId,
                car,
                customer,
                dealDate,
                salesRepresentative,
                paymentOptions,
                paymentSchedule);
    }

    public ValidationSummary validate() {
        CarDealDetails deal = buildCarDeal();
        return validator.validate(deal);
    }
}
