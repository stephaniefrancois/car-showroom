package core.deal.model;

import java.time.LocalDate;

public final class PaymentOptions {
    private final Integer durationInMonths;
    private final LocalDate firstPaymentDay;
    private final Integer deposit;

    public PaymentOptions(Integer durationInMonths, LocalDate firstPaymentDay, Integer deposit) {

        this.durationInMonths = durationInMonths;
        this.firstPaymentDay = firstPaymentDay;
        this.deposit = deposit;
    }

    public PaymentOptions(int durationInMonths, LocalDate firstPaymentDay) {

        this.durationInMonths = durationInMonths;
        this.firstPaymentDay = firstPaymentDay;
        this.deposit = 0;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public LocalDate getFirstPaymentDay() {
        return firstPaymentDay;
    }

    public Integer getDurationInMonths() {
        return durationInMonths;
    }
}
