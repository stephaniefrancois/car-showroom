package core.authentication.model;

public final class AuthenticationResult {
    private final UserProfile profile;

    public AuthenticationResult() {
        this.profile = null;
    }

    public AuthenticationResult(UserProfile profile) {
        this.profile = profile;
    }

    public boolean authenticated() {
        return profile != null;
    }

    public UserProfile getProfile() {
        return profile;
    }
}
