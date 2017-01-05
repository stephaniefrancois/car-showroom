package core.deal;

import core.domain.deal.PaymentOptions;
import core.domain.deal.PaymentSchedule;

import java.math.BigDecimal;

public interface PaymentScheduleCalculator {
    PaymentSchedule calculatePaymentSchedule(BigDecimal totalAmountToPay,
                                             PaymentOptions paymentOptions);
}
