package core.authentication;

import core.authentication.model.AuthenticationResult;

public interface UserAuthenticator {
    AuthenticationResult Authenticate(String userName, String password);
}
