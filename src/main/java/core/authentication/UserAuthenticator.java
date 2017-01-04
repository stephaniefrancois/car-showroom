package core.authentication;

import java.util.List;
import java.util.Objects;

public final class UserAuthenticator {
    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;

    public UserAuthenticator(UserRepository userRepository,
                             AuthenticationContext authenticationContext) {
        Objects.requireNonNull(userRepository,
                "'userRepository' must be supplied!");
        Objects.requireNonNull(authenticationContext,
                "'authenticationContext' must be supplied!");

        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }

    public AuthenticationResult Authenticate(String userName, String password) {
        List<User> users = userRepository.findUsersByCredentials(userName, password);

        if (users.isEmpty()) {
            authenticationContext.destroyEstablishedIdentity();
            return new AuthenticationResult();
        }

        User authenticatedUser = users.get(0);
        authenticationContext.authenticate(authenticatedUser);
        return new AuthenticationResult(authenticatedUser.getProfile());
    }
}
