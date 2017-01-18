package app.common.validation;

import core.validation.model.ValidationSummary;

import java.util.EventObject;
import java.util.Map;
import java.util.Objects;

public final class ValidationEventArgs extends EventObject {
    private final ValidationSummary validationSummary;
    private final Map<String, ValidateAbleFieldDescriptor> fieldsMap;

    public ValidationEventArgs(Object source,
                               ValidationSummary validationSummary,
                               Map<String, ValidateAbleFieldDescriptor> fieldsMap) {
        super(source);
        Objects.requireNonNull(validationSummary);
        Objects.requireNonNull(fieldsMap);
        this.validationSummary = validationSummary;
        this.fieldsMap = fieldsMap;
    }

    public ValidationSummary getValidationSummary() {
        return validationSummary;
    }

    public Map<String, ValidateAbleFieldDescriptor> getFieldsMap() {
        return fieldsMap;
    }
}
