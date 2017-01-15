package core.authentication;

import core.authentication.model.AuthenticationContext;
import core.authentication.model.NotAuthenticatedException;
import core.deal.SalesRepresentativeProvider;
import core.deal.model.SalesRepresentative;

import java.util.Objects;

public class AuthenticationContextBasedSalesRepresentativeProvider
        implements SalesRepresentativeProvider {

    private final AuthenticationContext authenticationContext;

    public AuthenticationContextBasedSalesRepresentativeProvider(
            AuthenticationContext authenticationContext) {
        Objects.requireNonNull(authenticationContext,
                "'authenticationContext' must be supplied!");

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
