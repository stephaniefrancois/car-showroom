package app.settings;

import common.ApplicationSettings;
import common.IRaiseEvents;
import common.ListenersManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Objects;

public final class SettingsDialog extends JDialog implements IRaiseEvents<SettingsEventListener> {

    private final JTextField serverField;
    private JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private SpinnerNumberModel spinnerModel;
    private JTextField userField;
    private JPasswordField passField;

    private ListenersManager<SettingsEventListener> listeners;

    public SettingsDialog(JFrame parent, ApplicationSettings currentSettings) {
        super(parent, "Preferences", false);
        Objects.requireNonNull(parent);
        this.listeners = new ListenersManager<>();

        spinnerModel = new SpinnerNumberModel(1433, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerModel);
        serverField = new JTextField(10);
        userField = new JTextField(10);
        passField = new JPasswordField(10);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        passField.setEchoChar('*');

        layoutControls();

        this.setDefaults(currentSettings);

        okButton.addActionListener(arg0 -> {
            Integer port = (Integer) portSpinner.getValue();
            String server = serverField.getText();
            String user = userField.getText();
            char[] password = passField.getPassword();
            ApplicationSettings settings = new ApplicationSettings(server, user, password, port);
            SettingsUpdatedEventArgs args = new SettingsUpdatedEventArgs(this, settings);
            this.listeners.notifyListeners(l -> l.settingsModified(args));
            setVisible(false);
        });

        cancelButton.addActionListener(arg0 -> setVisible(false));

        Dimension size = new Dimension(320, 230);
        setSize(size);
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(parent);
    }

    private void layoutControls() {
        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 15;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");

        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));

        controlsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;

        Insets rightPadding = new Insets(0, 0, 0, 15);
        Insets noPadding = new Insets(0, 0, 0, 0);

        // ///// First row /////////////////////////////

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Server: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        gc.fill = GridBagConstraints.HORIZONTAL;
        controlsPanel.add(serverField, gc);

        // ////// Next row ////////////////////////////

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("User: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        gc.fill = GridBagConstraints.HORIZONTAL;
        controlsPanel.add(userField, gc);

        // ////// Next row ////////////////////////////

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Password: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        gc.fill = GridBagConstraints.HORIZONTAL;
        controlsPanel.add(passField, gc);

        // ////// Next row ////////////////////////////

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Port: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(portSpinner, gc);

        // ////////// Buttons Panel ///////////////

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        Dimension btnSize = cancelButton.getPreferredSize();
        okButton.setPreferredSize(btnSize);

        // Add sub panels to dialog
        setLayout(new BorderLayout());
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void setDefaults(ApplicationSettings settings) {
        serverField.setText(settings.getServer());
        userField.setText(settings.getUser());
        portSpinner.setValue(settings.getPort());
    }

    @Override
    public void addListener(SettingsEventListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(SettingsEventListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }
}
