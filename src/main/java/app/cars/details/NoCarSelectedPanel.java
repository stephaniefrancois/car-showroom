package app.cars.details;

import app.styles.LabelStyles;

import javax.swing.*;
import java.awt.*;

public class NoCarSelectedPanel extends JPanel {
    public NoCarSelectedPanel() {
        setLayout(new BorderLayout());
        JLabel messageLabel = new JLabel("No car selected for preview ...");
        messageLabel.setFont(LabelStyles.getFontForHeaderLevelOne());
        add(messageLabel, BorderLayout.CENTER);
    }
}
