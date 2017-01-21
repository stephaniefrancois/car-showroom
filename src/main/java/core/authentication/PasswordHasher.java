package core.authentication;

public interface PasswordHasher {
    String hashPassword(String passwordToHash);
}
