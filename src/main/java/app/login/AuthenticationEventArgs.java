package app.login;

import core.authentication.model.AuthenticationResult;

import java.util.EventObject;
import java.util.Objects;

public final class AuthenticationEventArgs extends EventObject {
    private final AuthenticationResult authenticationResult;

    public AuthenticationEventArgs(Object source, AuthenticationResult authenticationResult) {
        super(source);
        Objects.requireNonNull(authenticationResult);
        this.authenticationResult = authenticationResult;
    }

    public AuthenticationResult getAuthenticationResult() {
        return authenticationResult;
    }
}
