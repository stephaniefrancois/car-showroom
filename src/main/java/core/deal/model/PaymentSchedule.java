package core.deal.model;

import java.math.BigDecimal;
import java.util.List;

public final class PaymentSchedule {
    private final BigDecimal totalAmountToPay;
    private final List<ScheduledPayment> scheduledPayments;

    public PaymentSchedule(BigDecimal totalAmountToPay, List<ScheduledPayment> scheduledPayments) {

        this.totalAmountToPay = totalAmountToPay;
        this.scheduledPayments = scheduledPayments;
    }

    public List<ScheduledPayment> getScheduledPayments() {
        return scheduledPayments;
    }

    public BigDecimal getTotalAmountToPay() {
        return totalAmountToPay;
    }
}
