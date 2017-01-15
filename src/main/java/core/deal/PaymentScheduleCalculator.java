package core.deal;

import core.deal.model.PaymentOptions;
import core.deal.model.PaymentSchedule;

import java.math.BigDecimal;

public interface PaymentScheduleCalculator {
    PaymentSchedule calculatePaymentSchedule(BigDecimal totalAmountToPay,
                                             PaymentOptions paymentOptions);
}
