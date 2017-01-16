package core.authentication.model;

public interface UserIdentity {
    boolean isAuthenticated();

    UserProfile getProfile();

    int getUserId();

    String getUserName();
}
