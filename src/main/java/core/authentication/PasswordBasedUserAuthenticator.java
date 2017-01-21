package core.authentication;

import core.authentication.model.AuthenticationContext;
import core.authentication.model.AuthenticationResult;
import core.authentication.model.User;
import data.UserRepository;

import java.util.List;
import java.util.Objects;

public final class PasswordBasedUserAuthenticator implements UserAuthenticator {
    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;
    private final PasswordHasher passwordHasher;

    public PasswordBasedUserAuthenticator(UserRepository userRepository,
                                          PasswordHasher passwordHasher,
                                          AuthenticationContext authenticationContext) {
        Objects.requireNonNull(userRepository,
                "'userRepository' must be supplied!");
        Objects.requireNonNull(passwordHasher,
                "'passwordHasher' must be supplied!");
        Objects.requireNonNull(authenticationContext,
                "'authenticationContext' must be supplied!");

        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public AuthenticationResult Authenticate(String userName, String password) {

        String hash = this.passwordHasher.hashPassword(password);
        List<User> users = userRepository.findUsersByCredentials(userName, hash);

        if (users.isEmpty()) {
            authenticationContext.destroyEstablishedIdentity();
            return new AuthenticationResult();
        }

        User authenticatedUser = users.get(0);
        authenticationContext.authenticate(authenticatedUser);
        return new AuthenticationResult(authenticatedUser.getProfile());
    }
}
