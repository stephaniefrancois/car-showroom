package core.deal;

import core.domain.deal.PaymentOptions;
import core.domain.deal.PaymentSchedule;
import core.domain.deal.ScheduledPayment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

        Date firstPaymentDate = paymentOptions
                .getFirstPaymentDay();

        BigDecimal amountToPayAfterDeposit =
                totalAmountToPay.subtract(new BigDecimal(paymentOptions.getDeposit()));

        BigDecimal paymentAmount = amountToPayAfterDeposit.divide(new BigDecimal(paymentsCount));

        for (int paymentNumber = 0; paymentNumber < paymentsCount; paymentNumber++) {

            // TODO: revisit DATE usage and its API, since most methods marked as DEPRECATED

            Date currentPaymentDate = new Date(firstPaymentDate.getYear(),
                    firstPaymentDate.getMonth() + paymentNumber,
                    firstPaymentDate.getDate());

            payments.add(new ScheduledPayment(paymentAmount, currentPaymentDate));
        }

        return new PaymentSchedule(amountToPayAfterDeposit, payments);
    }
}
