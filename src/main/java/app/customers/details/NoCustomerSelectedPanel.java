package app.customers.details;

import app.styles.LabelStyles;

import javax.swing.*;
import java.awt.*;

public class NoCustomerSelectedPanel extends JPanel {
    public NoCustomerSelectedPanel() {
        setLayout(new BorderLayout());
        JLabel messageLabel = new JLabel("No customer selected for preview ...");
        messageLabel.setFont(LabelStyles.getFontForHeaderLevelOne());
        add(messageLabel, BorderLayout.CENTER);
    }
}
