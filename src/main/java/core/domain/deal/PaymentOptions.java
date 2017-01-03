package core.domain.deal;

import java.util.Date;

public final class PaymentOptions {
    private final int durationInMonths;
    private final Date firstPaymentDay;
    private final int deposit;

    public PaymentOptions(int durationInMonths, Date firstPaymentDay, int deposit) {

        this.durationInMonths = durationInMonths;
        this.firstPaymentDay = firstPaymentDay;
        this.deposit = deposit;
    }

    public PaymentOptions(int durationInMonths, Date firstPaymentDay) {

        this.durationInMonths = durationInMonths;
        this.firstPaymentDay = firstPaymentDay;
        this.deposit = 0;
    }

    public int getDeposit() {
        return deposit;
    }

    public Date getFirstPaymentDay() {
        return firstPaymentDay;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }
}
