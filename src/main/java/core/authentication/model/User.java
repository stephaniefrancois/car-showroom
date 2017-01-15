package core.authentication.model;

import java.util.Objects;

public final class User {
    private final int userId;
    private final String userName;
    private final UserProfile profile;

    public User(int userId,
                String userName,
                UserProfile profile) {

        Objects.requireNonNull(profile,
                "'profile' must be supplied!");

        this.userId = userId;
        this.userName = userName;
        this.profile = profile;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public UserProfile getProfile() {
        return profile;
    }
}
