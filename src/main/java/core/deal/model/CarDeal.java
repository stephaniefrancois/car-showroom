package core.deal.model;

import core.IHaveIdentifier;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class CarDeal implements IHaveIdentifier {

    private final int carDealId;
    private final String carTitle;
    private final String customerFullName;
    private final LocalDate dealDate;
    private final BigDecimal dealAmount;
    private final int durationInMonths;

    public CarDeal(int carDealId,
                   String carTitle,
                   String customerFullName,
                   LocalDate dealDate,
                   BigDecimal dealAmount,
                   int durationInMonths) {
        this.carDealId = carDealId;
        this.carTitle = carTitle;
        this.customerFullName = customerFullName;
        this.dealDate = dealDate;
        this.dealAmount = dealAmount;
        this.durationInMonths = durationInMonths;
    }

    @Override
    public int getId() {
        return carDealId;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public LocalDate getDealDate() {
        return dealDate;
    }

    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }
}
