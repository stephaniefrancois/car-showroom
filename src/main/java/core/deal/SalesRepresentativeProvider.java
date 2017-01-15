package core.deal;

import core.authentication.model.NotAuthenticatedException;
import core.deal.model.SalesRepresentative;

public interface SalesRepresentativeProvider {
    SalesRepresentative getActiveSalesRepresentative() throws NotAuthenticatedException;
}
