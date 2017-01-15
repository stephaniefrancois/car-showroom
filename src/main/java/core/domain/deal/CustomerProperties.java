package core.domain.deal;

import core.domain.IHaveIdentifier;

import java.time.LocalDate;

public interface CustomerProperties extends IHaveIdentifier {
    String getLastName();

    String getFirstName();

    LocalDate getCustomerSince();

    String getCity();
}
