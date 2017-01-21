package data.inMemory;

import core.authentication.model.User;
import core.authentication.model.UserProfile;
import data.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class InMemoryUserRepository implements UserRepository {

    private final List<UserWithPassword> users;

    public InMemoryUserRepository() {
        users = Arrays.asList(
                new UserWithPassword("e52d98c459819a11775936d8dfbb7929", // Password -> secret1
                        new User(1, "Stephanie",
                                new UserProfile("Stephanie", "Francois"))),
                new UserWithPassword("e54cfb3714f76cedd4b27889e1f6a174", // Password -> secret2
                        new User(1, "John",
                                new UserProfile("John", "Brown"))),
                new UserWithPassword("1",
                        new User(1, "1",
                                new UserProfile("Demo User", "Brown")))
        );
    }

    @Override
    public List<User> findUsersByCredentials(String userName, String passwordHash) {
        return users.stream().filter(u -> u.getUser().getUserName().toLowerCase().equals(userName.toLowerCase())
                && (u.getPassword().equals(passwordHash))).map(u -> u.getUser()).collect(Collectors.toList());
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
