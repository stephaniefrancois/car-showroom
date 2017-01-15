package core.authentication;

import core.authentication.model.*;
import core.deal.SalesRepresentativeProvider;
import core.deal.model.SalesRepresentative;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class AuthenticationContextBasedSalesRepresentativeProviderTest {

    @Test
    public void GivenUserIsAuthenticatedWhenSalesRepIsRequestedThenSalesRepFromLoggedInUserShouldBeBuilt() throws NotAuthenticatedException {
        // Given
        int loggedInUserId = 99;
        AuthenticationContext context = new SimpleAuthenticationContext();
        User user = new User(loggedInUserId, "user1",
                new UserProfile("Stephanie", "Francois"));

        context.authenticate(user);

        SalesRepresentativeProvider sut =
                new AuthenticationContextBasedSalesRepresentativeProvider(context);
        // When
        SalesRepresentative result = sut.getActiveSalesRepresentative();

        // Then
        assertThat(result.getSalesRepresentativeId(), equalTo(loggedInUserId));
    }

    @Test
    public void GivenUserIsNotAuthenticatedWhenSalesRepIsRequestedThenThrows() {
        // Given
        AuthenticationContext context = new SimpleAuthenticationContext();
        SalesRepresentativeProvider sut =
                new AuthenticationContextBasedSalesRepresentativeProvider(context);
        // When
        // Then
        assertThrows(NotAuthenticatedException.class, sut::getActiveSalesRepresentative);
    }
}