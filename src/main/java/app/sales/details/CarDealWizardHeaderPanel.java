package app.sales.details;

import app.styles.BorderStyles;
import app.styles.LabelStyles;

import javax.swing.*;
import java.awt.*;

public final class CarDealWizardHeaderPanel extends JPanel {
    private final JLabel titleLabel;

    public CarDealWizardHeaderPanel(String initialTitle) {
        setLayout(new BorderLayout());
        titleLabel = new JLabel(initialTitle);
        titleLabel.setBorder(BorderStyles.getContentMargin());
        titleLabel.setFont(LabelStyles.getFontForHeaderLevelTwo());
        add(titleLabel, BorderLayout.CENTER);
    }

    public void setTitle(String title) {
        this.titleLabel.setText(title);
    }

    public void setTitle(String stepTitle, int currentStepNumber, int totalStepsCount) {
        String title = String.format("%s (%d out of %d total steps)", stepTitle, currentStepNumber, totalStepsCount);
        this.setTitle(title);
    }
}
