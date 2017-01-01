package core.authentication;

import core.domain.UserProfile;

public final class SimpleAuthenticationContext
        implements AuthenticationContext {

    private boolean authenticated = false;
    private UserProfile profile = null;
    private int userId = 0;
    private String userName = null;

    public SimpleAuthenticationContext() {
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public UserProfile getProfile() {
        return profile;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void authenticate(User authenticatedUser) {
        this.authenticated = true;
        this.profile = authenticatedUser.getProfile();
        this.userId = authenticatedUser.getUserId();
        this.userName = authenticatedUser.getUserName();
    }

    @Override
    public void destroyEstablishedIdentity() {
        this.authenticated = false;
        this.profile = null;
        this.userId = 0;
        this.userName = null;
    }
}
