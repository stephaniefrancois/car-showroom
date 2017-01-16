package core.authentication.model;

public interface AuthenticationContext extends UserIdentity {
    void authenticate(User authenticatedUser);

    void destroyEstablishedIdentity();
}

