package core.deal.model;

import core.IHaveIdentifier;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class ScheduledPayment implements IHaveIdentifier {
    private final BigDecimal amount;
    private final LocalDate paymentDate;

    public ScheduledPayment(BigDecimal amount, LocalDate paymentDate) {
        this(0, amount, paymentDate);
    }

    public ScheduledPayment(int id, BigDecimal amount, LocalDate paymentDate) {

        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    @Override
    public int getId() {
        return 0;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
