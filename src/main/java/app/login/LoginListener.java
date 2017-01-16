package app.login;

import java.util.EventListener;

public interface LoginListener extends EventListener {
    void loggedOn(AuthenticationEventArgs e);
}
