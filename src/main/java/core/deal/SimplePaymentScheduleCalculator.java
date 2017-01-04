package core.deal;

import core.domain.deal.PaymentOptions;
import core.domain.deal.PaymentSchedule;

import java.util.Objects;

public final class SimplePaymentScheduleCalculator
        implements PaymentScheduleCalculator {

    @Override
    public PaymentSchedule calculatePaymentSchedule(PaymentOptions paymentOptions) {
        Objects.requireNonNull(paymentOptions,
                "'PaymentOptions' must be supplied!");

        return null;
    }
}
