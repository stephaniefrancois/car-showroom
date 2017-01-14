package app.common;

import app.objectComposition.ServiceLocator;
import app.styles.BorderStyles;
import app.styles.LabelStyles;
import core.domain.validation.ValidationError;
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
        this.validationErrorsLabel.setBackground(LabelStyles.getBackgroundColorForFieldLabel());
        this.validationErrorsLabel.setLineWrap(true);
        this.validationErrorsLabel.setBorder(BorderStyles.getContentMargin());
        this.validationErrorsLabel.setEditable(false);
        add(this.validationErrorsLabel, BorderLayout.CENTER);
    }

    public void displayValidationResults(ValidationSummary validationSummary,
                                         Map<String, ValidateAbleFieldDescriptor> fieldsMap) {
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

    private void resetAllInvalidFields(Map<String, ValidateAbleFieldDescriptor> fieldsMap) {
        fieldsMap.values().forEach(ValidateAbleFieldDescriptor::markFieldAsValid);
    }

    private void markInvalidFields(ValidationSummary validationSummary,
                                   Map<String, ValidateAbleFieldDescriptor> fieldsMap) {
        List<String> invalidFieldsNames = validationSummary
                .getValidationErrors().stream()
                .map(ValidationError::getFieldName)
                .collect(Collectors.toList());

        for (String invalidFieldName : invalidFieldsNames) {
            if (fieldsMap.containsKey(invalidFieldName)) {
                ValidateAbleFieldDescriptor field = fieldsMap.get(invalidFieldName);
                field.markFieldAsInvalid(LabelStyles.getForegroundColorForInvalidFieldLabel(),
                        LabelStyles.getForegroundColorForInvalidField());
            }
        }
    }
}
