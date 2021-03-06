package app;

import app.login.LoginPanel;
import app.settings.SettingsDialog;
import app.settings.SettingsEventListener;
import app.settings.SettingsUpdatedEventArgs;
import app.styles.ComponentSizes;
import app.toolbar.ToolbarPanel;
import common.ApplicationSettings;
import common.SettingsStore;
import composition.ServiceLocator;
import core.authentication.model.UserIdentity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.logging.Logger;

public final class AppFrame extends JFrame {

    private final static Logger log = RootLogger.get();
    private final ToolbarPanel toolbar;
    private final ContainerPanel content;
    private final String toolbarItemToSelectKey = "ViewCars";
    private final UserIdentity identity;
    private final SettingsStore settingsStore;

    public AppFrame() throws HeadlessException {
        super("Car Showroom");

        configureSelf();
        toolbar = new ToolbarPanel();
        content = new ContainerPanel();
        // TODO: use perhaps use IOC container ?
        settingsStore = ServiceLocator.getComposer().getSettingsStore();
        identity = ServiceLocator.getComposer().getUserIdentity();

        this.add(toolbar, BorderLayout.NORTH);
        this.add(content, BorderLayout.CENTER);

        toolbar.addListener(e -> {
            switch (e.getMenuItemKey()) {
                case "ViewCars": {
                    content.navigateToCars();
                    break;
                }
                case "ViewCustomers": {
                    content.navigateToCustomers();
                    break;
                }
                case "ViewSales": {
                    content.navigateToSales();
                    break;
                }
                case "ViewSettings": {
                    showSettings();
                    break;
                }
                default: {
                    System.out.println("Don't know how to navigate to '" + e.getMenuItemKey() + "'");
                }
            }
        });
        promptToLogin();
    }

    private void showSettings() {
        ApplicationSettings settings = this.settingsStore.getSettings();
        SettingsDialog settingsDialog = new SettingsDialog(this, settings);
        settingsDialog.addListener(new SettingsEventListener() {
            @Override
            public void settingsModified(SettingsUpdatedEventArgs args) {
                logApplicationSettingsUpdated(args);
                settingsStore.saveSettings(args.getSettings());
            }
        });
        settingsDialog.setVisible(true);
    }
    private void promptToLogin() {
        Dimension preferredSize = new Dimension(400, 250);

        final LoginPanel loginPanel = new LoginPanel();
        final JDialog frame = new JDialog(this, "Login", true);
        frame.setSize(preferredSize);
        frame.setLocationRelativeTo(this);
        frame.setResizable(false);
        frame.getContentPane().add(loginPanel);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                logClosedBeforeLoggedIn();
                System.exit(0);
            }
        });
        loginPanel.addListener(e -> {
            if (identity.isAuthenticated()) {
                logUserLoggedIn();
                frame.setVisible(false);
                frame.dispose();
                AppFrame.this.setVisible(true);
                AppFrame.this.setTitle(String.format("%s (Logged in as %s %s)",
                        AppFrame.this.getTitle(),
                        identity.getProfile().getFirstName(),
                        identity.getProfile().getLastName()));
                toolbar.setActiveToolbarItem(toolbarItemToSelectKey);
            } else {
                logClosedBeforeLoggedIn();
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    private void configureSelf() {
        setMinimumSize(ComponentSizes.APP_SIZE);
        setSize(getMinimumSize());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        pack();
        setLocationRelativeTo(null);
    }

    private void logUserLoggedIn() {
        log.info(() -> String.format("'%s' has logged in successfully!", identity.getUserName()));
    }

    private void logClosedBeforeLoggedIn() {
        log.warning(() -> "User has closed login screen before logged in! Terminating application ...");
    }

    private void logApplicationSettingsUpdated(SettingsUpdatedEventArgs args) {
        log.info(() -> String.format("Application settings set to: %s", args.getSettings()));
    }

}
