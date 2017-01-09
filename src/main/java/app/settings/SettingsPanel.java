package app.settings;

import javax.swing.*;
import java.awt.*;

public final class SettingsPanel extends JPanel {
    public SettingsPanel() {
        setLayout(new BorderLayout());
        add(new Label("I am a settings panel :)"));
    }
}
