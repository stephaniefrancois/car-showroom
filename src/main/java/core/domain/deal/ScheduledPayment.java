package core.domain.deal;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class ScheduledPayment {
    private final BigDecimal amount;
    private final LocalDate paymentDate;

    public ScheduledPayment(BigDecimal amount, LocalDate paymentDate) {

        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
