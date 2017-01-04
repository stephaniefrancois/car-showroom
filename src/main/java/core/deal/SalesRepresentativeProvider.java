package core.deal;

import core.authentication.NotAuthenticatedException;
import core.domain.deal.SalesRepresentative;

public interface SalesRepresentativeProvider {
    SalesRepresentative getActiveSalesRepresentative() throws NotAuthenticatedException;
}
