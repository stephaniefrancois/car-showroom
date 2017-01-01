package core.authentication;

import java.util.List;

public interface UserRepository {
    List<User> findUsersByCredentials(String userName, String password);
}
