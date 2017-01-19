package app.login;

import app.RootLogger;
import app.styles.BorderStyles;
import app.styles.LabelStyles;
import common.IRaiseEvents;
import common.ListenersManager;
import composition.ServiceLocator;
import core.authentication.UserAuthenticator;
import core.authentication.model.AuthenticationResult;
import resources.ResourceProvider;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public final class LoginPanel extends JPanel implements IRaiseEvents<LoginListener> {
    private final static Logger log = RootLogger.get();
    private final Insets controlsPadding;
    private final GridBagConstraints formGridConfig;
    private final UserAuthenticator authenticator;
    private final ListenersManager<LoginListener> listeners;

    public LoginPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderStyles.getContentMargin());

        listeners = new ListenersManager<>();
        authenticator = ServiceLocator.getComposer().getUserAuthenticator();

        this.controlsPadding = new Insets(5, 0, 10, 25);
        formGridConfig = new GridBagConstraints();
        formGridConfig.fill = GridBagConstraints.NONE;

        JTextField userNameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        addControlWithLabel(userNameField, "User Name", 0);
        addControlWithLabel(passwordField, "Password", 1);

        formGridConfig.gridy = 2;
        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;
        formGridConfig.gridx = 1;
        formGridConfig.anchor = GridBagConstraints.FIRST_LINE_START;
        formGridConfig.insets = new Insets(25, 0, 5, 25);
        add(loginButton, formGridConfig);

        loginButton.addActionListener(e -> {
            logAuthenticating();
            String userName = userNameField.getText();
            AuthenticationResult result = authenticator.Authenticate(userName,
                    String.valueOf(passwordField.getPassword()));

            if (result.authenticated()) {
                logAuthenticated(userName);
                AuthenticationEventArgs args = new AuthenticationEventArgs(e.getSource(), result);
                listeners.notifyListeners(l -> l.loggedOn(args));
                return;
            }

            logLoginFailed(userName);
            userNameField.setBackground(LabelStyles.getForegroundColorForInvalidField());
            passwordField.setBackground(LabelStyles.getForegroundColorForInvalidField());
        });
    }

    private void addControlWithLabel(Component componentToAdd, String label, int rowIndex) {
        JLabel componentLabel = new JLabel(label);
        componentLabel.setLabelFor(componentToAdd);
        componentLabel.setForeground(LabelStyles.getBrightForegroundColorForFieldLabel());
        componentLabel.setFont(LabelStyles.getFontForFieldLabel());
        componentToAdd.setFont(LabelStyles.getFontForFieldLabel());

        formGridConfig.gridy = rowIndex;
        formGridConfig.gridx = 0;
        formGridConfig.weightx = 0.1;
        formGridConfig.weighty = 0.1;

        formGridConfig.fill = GridBagConstraints.NONE;
        formGridConfig.anchor = GridBagConstraints.LINE_END;
        formGridConfig.insets = this.controlsPadding;
        add(componentLabel, formGridConfig);

        formGridConfig.gridx = 1;
        formGridConfig.weightx = 0.4;
        formGridConfig.insets = this.controlsPadding;
        formGridConfig.anchor = GridBagConstraints.LINE_START;
        formGridConfig.fill = GridBagConstraints.HORIZONTAL;
        add(componentToAdd, formGridConfig);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ResourceProvider.getLoginBackgroundIcon().getImage(), 0, 0, null);
    }

    @Override
    public void addListener(LoginListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(LoginListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }

    private void logAuthenticating() {
        log.info(() -> "Authenticating ...");
    }

    private void logAuthenticated(String userName) {
        log.info(() -> String.format("'%s' has authenticated successfully!", userName));
    }

    private void logLoginFailed(String userName) {
        log.warning(() -> String.format("Login for '%s' user has failed!", userName));
    }
}
