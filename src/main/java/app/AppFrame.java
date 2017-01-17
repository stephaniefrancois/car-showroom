package app;

import app.login.AuthenticationEventArgs;
import app.login.LoginListener;
import app.login.LoginPanel;
import app.objectComposition.ServiceLocator;
import app.styles.ComponentSizes;
import app.toolbar.ToolbarPanel;
import core.authentication.model.UserIdentity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.logging.Logger;

public final class AppFrame extends JFrame {

    private final static Logger log = RootLogger.get();
    private final ToolbarPanel toolbar;
    private final ContentPanel content;
    private final String toolbarItemToSelectKey = "ViewCars";
    private final UserIdentity identity;

    public AppFrame() throws HeadlessException {
        super("Car Showroom");

        configureSelf();
        toolbar = new ToolbarPanel();
        content = new ContentPanel();
        this.add(toolbar, BorderLayout.NORTH);
        this.add(content, BorderLayout.CENTER);

        identity = ServiceLocator.getComposer().getUserIdentity();

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
                case "ViewReports": {
                    content.navigateToReports();
                    break;
                }
                case "ViewSettings": {
                    content.navigateToSettings();
                    break;
                }
                default: {
                    System.out.println("Don't know how to navigate to '" + e.getMenuItemKey() + "'");
                }
            }
        });

        toolbar.setActiveToolbarItem(toolbarItemToSelectKey);
        promptToLogin();
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
        loginPanel.addListener(new LoginListener() {
            @Override
            public void loggedOn(AuthenticationEventArgs e) {
                if (identity.isAuthenticated()) {
                    logUserLoggedIn();
                    frame.setVisible(false);
                    frame.dispose();
                    AppFrame.this.setVisible(true);
                    AppFrame.this.setTitle(String.format("%s (Logged in as %s %s)",
                            AppFrame.this.getTitle(),
                            identity.getProfile().getFirstName(),
                            identity.getProfile().getLastName()));
                    return;
                } else {
                    logClosedBeforeLoggedIn();
                    System.exit(0);
                }
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
}
