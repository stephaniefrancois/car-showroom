package core.domain.deal;

import java.math.BigDecimal;
import java.util.Date;

public final class ScheduledPayment {
    private final BigDecimal amount;
    private final Date paymentDate;

    public ScheduledPayment(BigDecimal amount, Date paymentDate) {

        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
