package core.authentication;

import core.authentication.model.AuthenticationContext;
import core.authentication.model.SimpleAuthenticationContext;
import core.authentication.model.User;
import core.authentication.model.UserProfile;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public final class SimpleAuthenticationContextTest {
    @Test
    public void GivenEmptyAuthenticationContextWhenCheckingStatusThenWeShouldNotBeAuthenticated() {
        // Given
        AuthenticationContext sut = new SimpleAuthenticationContext();

        // When
        boolean result = sut.isAuthenticated();

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void GivenWeAuthenticateWhenCheckingStatusThenWeShouldBeAuthenticated() {
        // Given
        AuthenticationContext sut = new SimpleAuthenticationContext();
        sut.authenticate(new User(1, "name",
                new UserProfile("fname", "lname")));

        // When
        boolean result = sut.isAuthenticated();

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void GivenWeAuthenticateWhenCheckingProfileThenWeShouldHaveUserProfileInformation() {
        // Given
        AuthenticationContext sut = new SimpleAuthenticationContext();
        sut.authenticate(new User(1, "name",
                new UserProfile("fname", "lname")));

        // When
        UserProfile result = sut.getProfile();

        // Then
        assertThat(result.getFirstName(), equalTo("fname"));
        assertThat(result.getLastName(), equalTo("lname"));
    }

    @Test
    public void GivenWeAuthenticateWhenCheckingIdThenWeShouldHaveCorrectUserId() {
        // Given
        AuthenticationContext sut = new SimpleAuthenticationContext();
        sut.authenticate(new User(10, "name",
                new UserProfile("fname", "lname")));

        // When
        int result = sut.getUserId();

        // Then
        assertThat(result, equalTo(10));
    }

    @Test
    public void GivenWeAuthenticateWhenCheckingUserNameThenWeShouldHaveCorrectUserName() {
        // Given
        AuthenticationContext sut = new SimpleAuthenticationContext();
        sut.authenticate(new User(10, "userName",
                new UserProfile("fname", "lname")));

        // When
        String result = sut.getUserName();

        // Then
        assertThat(result, equalTo("userName"));
    }

    @Test
    public void GivenAuthenticatedContextWhenWeDestroyIdentityThenWeShouldNotBeAuthenticated() {
        // Given
        AuthenticationContext sut = new SimpleAuthenticationContext();
        sut.authenticate(new User(10, "name",
                new UserProfile("fname", "lname")));

        // When
        sut.destroyEstablishedIdentity();

        // Then
        assertThat(sut.isAuthenticated(), equalTo(false));
        assertThat(sut.getUserId(), equalTo(0));
        assertThat(sut.getUserName(), is(nullValue()));
        assertThat(sut.getProfile(), is(nullValue()));
    }
}