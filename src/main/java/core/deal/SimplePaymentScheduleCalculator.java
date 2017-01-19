package core.deal;

import core.deal.model.PaymentOptions;
import core.deal.model.PaymentSchedule;
import core.deal.model.ScheduledPayment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SimplePaymentScheduleCalculator
        implements PaymentScheduleCalculator {

    @Override
    public PaymentSchedule calculatePaymentSchedule(BigDecimal totalAmountToPay,
                                                    PaymentOptions paymentOptions) {
        Objects.requireNonNull(totalAmountToPay,
                "'totalAmountToPay' must be supplied!");
        Objects.requireNonNull(paymentOptions,
                "'PaymentOptions' must be supplied!");

        List<ScheduledPayment> payments = new ArrayList<>();
        int paymentsCount = paymentOptions.getDurationInMonths();

        LocalDate firstPaymentDate = paymentOptions
                .getFirstPaymentDay();

        BigDecimal amountToPayAfterDeposit =
                totalAmountToPay.subtract(new BigDecimal(paymentOptions.getDeposit()));

        BigDecimal paymentAmount = amountToPayAfterDeposit.divide(new BigDecimal(paymentsCount)
                , 2, RoundingMode.HALF_UP);

        for (int paymentNumber = 0; paymentNumber < paymentsCount; paymentNumber++) {
            LocalDate currentPaymentDate = firstPaymentDate.plusMonths(paymentNumber);
            payments.add(new ScheduledPayment(paymentAmount, currentPaymentDate));
        }

        return new PaymentSchedule(amountToPayAfterDeposit, payments);
    }
}
