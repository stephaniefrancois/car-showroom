package core.deal;

import core.deal.model.PaymentOptions;
import core.deal.model.PaymentSchedule;
import core.deal.model.ScheduledPayment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public final class SimplePaymentScheduleCalculatorTest {

    @Test
    public void GivenPaymentOptionsWhenScheduleRequestedFor3MonthsThenCalculateSimplePaymentSchedule() {
        // Given
        int durationInMonths = 3;
        LocalDate firstPaymentDate = LocalDate.of(2017, Month.JANUARY, 15);
        BigDecimal totalAmountToPay = new BigDecimal(3000);
        PaymentOptions options = new PaymentOptions(durationInMonths, firstPaymentDate);
        PaymentScheduleCalculator sut = new SimplePaymentScheduleCalculator();

        // When
        PaymentSchedule schedule = sut.calculatePaymentSchedule(totalAmountToPay, options);

        // Then
        assertThat(schedule.getScheduledPayments(), hasSize(3));

        ScheduledPayment firstPayment = schedule.getScheduledPayments().get(0);
        ScheduledPayment secondPayment = schedule.getScheduledPayments().get(1);
        ScheduledPayment thirdPayment = schedule.getScheduledPayments().get(2);

        assertThat(firstPayment.getAmount(), equalTo(new BigDecimal(1000)));
        assertThat(firstPayment.getPaymentDate(), equalTo(LocalDate.of(2017, Month.JANUARY, 15)));

        assertThat(secondPayment.getAmount(), equalTo(new BigDecimal(1000)));
        assertThat(secondPayment.getPaymentDate(), equalTo(LocalDate.of(2017, Month.FEBRUARY, 15)));

        assertThat(thirdPayment.getAmount(), equalTo(new BigDecimal(1000)));
        assertThat(thirdPayment.getPaymentDate(), equalTo(LocalDate.of(2017, Month.MARCH, 15)));
    }

    @Test
    public void GivenPaymentOptionsWhenScheduleRequestedFor6MonthsThenCalculateSimplePaymentSchedule() {
        // Given
        int durationInMonths = 6;
        LocalDate firstPaymentDate = LocalDate.of(2017, 1, 15);
        BigDecimal totalAmountToPay = new BigDecimal(3000);
        PaymentOptions options = new PaymentOptions(durationInMonths, firstPaymentDate);
        PaymentScheduleCalculator sut = new SimplePaymentScheduleCalculator();

        // When
        PaymentSchedule schedule = sut.calculatePaymentSchedule(totalAmountToPay, options);

        // Then
        assertThat(schedule.getScheduledPayments(), hasSize(6));

        ScheduledPayment firstPayment = schedule.getScheduledPayments().get(0);
        ScheduledPayment lastPayment = schedule.getScheduledPayments().get(5);

        assertThat(firstPayment.getAmount(), equalTo(new BigDecimal(500)));
        assertThat(firstPayment.getPaymentDate(), equalTo(LocalDate.of(2017, Month.JANUARY, 15)));

        assertThat(lastPayment.getAmount(), equalTo(new BigDecimal(500)));
        assertThat(lastPayment.getPaymentDate(), equalTo(LocalDate.of(2017, Month.JUNE, 15)));
    }

    @Test
    public void GivenPaymentOptionsWhenScheduleRequestedFor3MonthsWith600DepositThenCalculateSimplePaymentSchedule() {
        // Given
        int durationInMonths = 3;
        LocalDate firstPaymentDate = LocalDate.of(2017, 1, 15);
        BigDecimal totalAmountToPay = new BigDecimal(3000);
        int deposit = 600;
        PaymentOptions options = new PaymentOptions(durationInMonths, firstPaymentDate, deposit);
        PaymentScheduleCalculator sut = new SimplePaymentScheduleCalculator();
        BigDecimal leftToPayAfterDeposit = new BigDecimal(2400);

        // When
        PaymentSchedule schedule = sut.calculatePaymentSchedule(totalAmountToPay, options);

        // Then
        assertThat(schedule.getScheduledPayments(), hasSize(3));
        assertThat(schedule.getTotalAmountToPay(), equalTo(leftToPayAfterDeposit));

        ScheduledPayment firstPayment = schedule.getScheduledPayments().get(0);
        ScheduledPayment secondPayment = schedule.getScheduledPayments().get(1);
        ScheduledPayment thirdPayment = schedule.getScheduledPayments().get(2);

        assertThat(firstPayment.getAmount(), equalTo(new BigDecimal(800)));
        assertThat(firstPayment.getPaymentDate(), equalTo(LocalDate.of(2017, Month.JANUARY, 15)));

        assertThat(secondPayment.getAmount(), equalTo(new BigDecimal(800)));
        assertThat(secondPayment.getPaymentDate(), equalTo(LocalDate.of(2017, Month.FEBRUARY, 15)));

        assertThat(thirdPayment.getAmount(), equalTo(new BigDecimal(800)));
        assertThat(thirdPayment.getPaymentDate(), equalTo(LocalDate.of(2017, Month.MARCH, 15)));
    }
}