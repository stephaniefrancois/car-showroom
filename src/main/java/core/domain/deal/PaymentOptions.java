package core.domain.deal;

import java.time.LocalDate;

public final class PaymentOptions {
    private final int durationInMonths;
    private final LocalDate firstPaymentDay;
    private final int deposit;

    public PaymentOptions(int durationInMonths, LocalDate firstPaymentDay, int deposit) {

        this.durationInMonths = durationInMonths;
        this.firstPaymentDay = firstPaymentDay;
        this.deposit = deposit;
    }

    public PaymentOptions(int durationInMonths, LocalDate firstPaymentDay) {

        this.durationInMonths = durationInMonths;
        this.firstPaymentDay = firstPaymentDay;
        this.deposit = 0;
    }

    public int getDeposit() {
        return deposit;
    }

    public LocalDate getFirstPaymentDay() {
        return firstPaymentDay;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }
}
