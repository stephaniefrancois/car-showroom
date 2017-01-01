package core.authentication;

import core.domain.UserProfile;

public interface AuthenticationContext {
    boolean isAuthenticated();

    UserProfile getProfile();

    int getUserId();

    String getUserName();

    void authenticate(User authenticatedUser);

    void destroyEstablishedIdentity();
}
