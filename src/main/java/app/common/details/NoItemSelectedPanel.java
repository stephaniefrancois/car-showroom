package app.common.details;

import app.styles.LabelStyles;

import javax.swing.*;
import java.awt.*;

public final class NoItemSelectedPanel extends JPanel {
    public NoItemSelectedPanel(String message) {
        setLayout(new BorderLayout());
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(LabelStyles.getFontForHeaderLevelOne());
        add(messageLabel, BorderLayout.CENTER);
    }
}
