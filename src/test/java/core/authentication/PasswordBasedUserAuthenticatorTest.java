package core.authentication;

import core.authentication.model.AuthenticationContext;
import core.authentication.model.AuthenticationResult;
import core.authentication.model.User;
import core.authentication.model.UserProfile;
import data.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public final class PasswordBasedUserAuthenticatorTest {

    @Test
    public void GivenCorrectUserCredentialsShouldAuthenticateUser() {
        // Given
        String userName = "user1";
        String password = "secret";

        List<User> users = new ArrayList<>();
        users.add(new User(1, "user1", new UserProfile("Stephanie", "Francois")));

        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        PasswordHasher hasherMock = Mockito.mock(PasswordHasher.class);
        AuthenticationContext authenticationContextMock = Mockito.mock(AuthenticationContext.class);
        PasswordBasedUserAuthenticator sut = new PasswordBasedUserAuthenticator(userRepositoryMock, hasherMock, authenticationContextMock);

        when(userRepositoryMock.findUsersByCredentials("user1", "secret")).thenReturn(users);

        // When
        AuthenticationResult result = sut.Authenticate(userName, password);

        // Then
        assertThat(result.authenticated(), is(true));
        assertThat(result.getProfile().getFirstName(), equalTo("Stephanie"));
        assertThat(result.getProfile().getLastName(), equalTo("Francois"));
    }

    @Test
    public void GivenInCorrectUserCredentialsUserShouldNotBeAuthenticated() {
        // Given
        String userName = "user1";
        String password = "badSecret";
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        PasswordHasher hasherMock = Mockito.mock(PasswordHasher.class);
        AuthenticationContext authenticationContextMock = Mockito.mock(AuthenticationContext.class);

        PasswordBasedUserAuthenticator sut = new PasswordBasedUserAuthenticator(userRepositoryMock, hasherMock, authenticationContextMock);
        // When
        AuthenticationResult result = sut.Authenticate(userName, password);

        // Then
        assertThat(result.authenticated(), is(false));
    }

    @Test
    public void GivenUserIsAuthenticatedUserProfileShouldBeEstablished() {
        // Given
        int userId = 10;

        List<User> users = new ArrayList<>();
        users.add(new User(userId, "user1", new UserProfile("Stephanie", "Francois")));

        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        AuthenticationContext authenticationContextMock = Mockito.mock(AuthenticationContext.class);
        PasswordHasher hasherMock = Mockito.mock(PasswordHasher.class);
        PasswordBasedUserAuthenticator sut = new PasswordBasedUserAuthenticator(userRepositoryMock, hasherMock, authenticationContextMock);

        when(userRepositoryMock.findUsersByCredentials("user1", "badSecret")).thenReturn(users);

        // When
        AuthenticationResult result = sut.Authenticate("user1", "badSecret");

        // Then
        final ArgumentCaptor<User> userArgument
                = ArgumentCaptor.forClass((Class) User.class);

        verify(authenticationContextMock).authenticate(userArgument.capture());
        assertThat(userArgument.getValue().getUserId(), equalTo(userId));
    }

    @Test
    public void GivenUserHasFailedToReAuthenticateThenAuthenticatedUserProfileShouldBeRemoved() {
        // Given
        List<User> users = new ArrayList<>();
        users.add(new User(1, "user1", new UserProfile("Stephanie", "Francois")));

        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        AuthenticationContext authenticationContextMock = Mockito.mock(AuthenticationContext.class);
        PasswordHasher hasherMock = Mockito.mock(PasswordHasher.class);
        PasswordBasedUserAuthenticator sut = new PasswordBasedUserAuthenticator(userRepositoryMock, hasherMock, authenticationContextMock);

        when(userRepositoryMock.findUsersByCredentials("user1", "badSecret")).thenReturn(users);
        sut.Authenticate("user1", "badSecret");
        // When
        AuthenticationResult result = sut.Authenticate("user1", "wrongPassword");

        // Then
        verify(authenticationContextMock, times(1)).destroyEstablishedIdentity();
    }
}