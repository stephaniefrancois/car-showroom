package app.cars.details;

import app.objectComposition.ServiceLocator;
import app.styles.BorderStyles;
import app.styles.LabelStyles;
import core.domain.validation.ValidationSummary;
import core.validation.ValidationErrorsFormatter;

import javax.swing.*;
import java.awt.*;

public final class ValidationSummaryPanel extends JPanel {

    private final ValidationErrorsFormatter validationErrorsFormatter;
    private final JTextArea validationErrorsLabel;

    public ValidationSummaryPanel() {
        super(new BorderLayout());
        // TODO: replace locator with Constructor Injection
        this.validationErrorsFormatter = ServiceLocator.getComposer().getValidationErrorsFormatter();
        this.validationErrorsLabel = new JTextArea();
        this.validationErrorsLabel.setBackground(LabelStyles.getBackgroudColorForFieldLabel());
        this.validationErrorsLabel.setLineWrap(true);
        this.validationErrorsLabel.setBorder(BorderStyles.getContentMargin());
        add(this.validationErrorsLabel, BorderLayout.CENTER);
    }

    public void displayValidationResults(ValidationSummary validationSummary) {
        if (validationSummary.getIsValid()) {
            this.validationErrorsLabel.setText("");
            setVisible(false);
            return;
        }

        String formattedErrors = this.validationErrorsFormatter.format(validationSummary);
        this.validationErrorsLabel.setText(formattedErrors);
        setVisible(true);
    }
}
