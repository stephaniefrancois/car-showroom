package core.authentication.model;

public final class UserProfile {
    private final String firstName;
    private final String lastName;

    public UserProfile(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
