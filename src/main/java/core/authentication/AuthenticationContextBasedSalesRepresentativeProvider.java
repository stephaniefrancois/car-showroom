package core.authentication;

import core.deal.SalesRepresentativeProvider;
import core.domain.deal.SalesRepresentative;

public class AuthenticationContextBasedSalesRepresentativeProvider
        implements SalesRepresentativeProvider {

    private final AuthenticationContext authenticationContext;

    public AuthenticationContextBasedSalesRepresentativeProvider(
            AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    @Override
    public SalesRepresentative getActiveSalesRepresentative() throws NotAuthenticatedException {
        if (!authenticationContext.isAuthenticated()) {
            throw new NotAuthenticatedException();
        }

        return new SalesRepresentative(
                authenticationContext.getUserId(),
                authenticationContext.getProfile()
        );
    }
}
