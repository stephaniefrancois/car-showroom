package app.common.validation;

import app.RootLogger;
import app.objectComposition.ServiceLocator;
import app.styles.BorderStyles;
import app.styles.LabelStyles;
import core.validation.ValidationErrorsFormatter;
import core.validation.model.ValidationError;
import core.validation.model.ValidationSummary;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class ValidationSummaryPanel extends JPanel {

    private static final Logger log = RootLogger.get();
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

    public final void displayValidationResults(ValidationSummary validationSummary,
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
        logValidationErrors(formattedErrors);
        setVisible(true);
    }

    public final void clearValidationResults(Map<String, ValidateAbleFieldDescriptor> fieldsMap) {
        Objects.requireNonNull(fieldsMap);
        resetAllInvalidFields(fieldsMap);
        this.validationErrorsLabel.setText("");
        setVisible(false);
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

    private void logValidationErrors(String formattedErrors) {
        log.warning(() -> formattedErrors);
    }
}
