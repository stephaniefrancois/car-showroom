package core.domain.deal;

import java.time.LocalDate;

public interface CustomerProperties {
    int getCustomerId();

    String getLastName();

    String getFirstName();

    LocalDate getCustomerSince();

    String getCity();
}
