package app.common;

import app.objectComposition.ServiceLocator;
import app.styles.BorderStyles;
import app.styles.LabelStyles;
import core.domain.validation.ValidationSummary;
import core.validation.ValidationErrorsFormatter;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public void displayValidationResults(ValidationSummary validationSummary,
                                         Map<String, ValidateableFieldDescriptor> fieldsMap) {
        Objects.requireNonNull(validationSummary);
        Objects.requireNonNull(fieldsMap);

        resetAllInvalidFields(fieldsMap);

        if (validationSummary.getIsValid()) {
            this.validationErrorsLabel.setText("");
            setVisible(false);
            return;
        }

        this.markInvalidFields(validationSummary, fieldsMap);

        String formattedErrors = this.validationErrorsFormatter.format(validationSummary);
        this.validationErrorsLabel.setText(formattedErrors);
        setVisible(true);
    }

    private void resetAllInvalidFields(Map<String, ValidateableFieldDescriptor> fieldsMap) {
        fieldsMap.values().forEach(d -> d.markFieldAsValid());
    }

    private void markInvalidFields(ValidationSummary validationSummary,
                                   Map<String, ValidateableFieldDescriptor> fieldsMap) {
        List<String> invalidFieldsNames = validationSummary
                .getValidationErrors().stream()
                .map(e -> e.getFieldName())
                .collect(Collectors.toList());

        for (String invalidFieldName : invalidFieldsNames) {
            if (fieldsMap.containsKey(invalidFieldName)) {
                ValidateableFieldDescriptor field = fieldsMap.get(invalidFieldName);
                field.markFieldAsInvalid(LabelStyles.getForegroundColorForInvalidFieldLabel(),
                        LabelStyles.getForegroundColorForInvalidField());
            }
        }
    }
}
