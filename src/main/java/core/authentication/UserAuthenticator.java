package core.authentication;

import core.authentication.model.AuthenticationResult;

/**
 * Created by aurim on 16/01/2017.
 */
public interface UserAuthenticator {
    AuthenticationResult Authenticate(String userName, String password);
}
