package app.cars.details.features;

import app.styles.LabelStyles;

import javax.swing.*;
import java.awt.*;

public final class NoCarFeaturesPanel extends JPanel {
    public NoCarFeaturesPanel() {
        setLayout(new BorderLayout());
        JLabel messageLabel = new JLabel("Sadly this car has 0 features :)");
        messageLabel.setFont(LabelStyles.getFontForFieldLabel());
        add(messageLabel, BorderLayout.CENTER);
    }
}