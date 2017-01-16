package data;

import core.authentication.UserRepository;
import core.authentication.model.User;
import core.authentication.model.UserProfile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class InMemoryUserRepository implements UserRepository {

    private final List<UserWithPassword> users;

    public InMemoryUserRepository() {
        users = Arrays.asList(
                new UserWithPassword("secret1",
                        new User(1, "Stephanie",
                                new UserProfile("Stephanie", "Francois"))),
                new UserWithPassword("secret2",
                        new User(1, "John",
                                new UserProfile("John", "Brown")))
        );
    }

    @Override
    public List<User> findUsersByCredentials(String userName, String password) {
        return users.stream().filter(u -> u.getUser().getUserName().toLowerCase().equals(userName.toLowerCase())
                && (u.getPassword().equals(password))).map(u -> u.getUser()).collect(Collectors.toList());
    }

    private final class UserWithPassword {
        private final String password;
        private final User user;

        public UserWithPassword(String password, User user) {
            this.password = password;
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }
    }
}
