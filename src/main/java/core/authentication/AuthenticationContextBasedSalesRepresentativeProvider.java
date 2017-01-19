package core.authentication;

import core.authentication.model.NotAuthenticatedException;
import core.authentication.model.UserIdentity;
import core.deal.SalesRepresentativeProvider;
import core.deal.model.SalesRepresentative;

import java.util.Objects;

public class AuthenticationContextBasedSalesRepresentativeProvider
        implements SalesRepresentativeProvider {

    private final UserIdentity userIdentity;

    public AuthenticationContextBasedSalesRepresentativeProvider(
            UserIdentity userIdentity) {
        Objects.requireNonNull(userIdentity,
                "'userIdentity' must be supplied!");

        this.userIdentity = userIdentity;
    }

    @Override
    public SalesRepresentative getActiveSalesRepresentative() throws NotAuthenticatedException {
        if (!userIdentity.isAuthenticated()) {
            throw new NotAuthenticatedException();
        }

        return new SalesRepresentative(
                userIdentity.getUserId(),
                userIdentity.getProfile()
        );
    }
}
