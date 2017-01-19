package app.sales.details.wizard;

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

    public void setTitle(String stepTitle, int currentStepNumber, int totalStepsCount) {
        String title = String.format("%s (%d out of %d total steps)", stepTitle, currentStepNumber, totalStepsCount);
        this.titleLabel.setText(title);
    }
}
