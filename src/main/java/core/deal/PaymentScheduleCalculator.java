package core.deal;

import core.domain.deal.PaymentOptions;
import core.domain.deal.PaymentSchedule;

public interface PaymentScheduleCalculator {
    PaymentSchedule calculatePaymentSchedule(PaymentOptions paymentOptions);
}
