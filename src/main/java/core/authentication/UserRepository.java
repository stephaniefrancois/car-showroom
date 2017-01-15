package core.authentication;

import core.authentication.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findUsersByCredentials(String userName, String password);
}
