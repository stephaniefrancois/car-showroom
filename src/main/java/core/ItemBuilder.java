package core;

import core.validation.model.ValidationException;
import core.validation.model.ValidationSummary;

public interface ItemBuilder<TModel> extends IHaveIdentifier {
    TModel build() throws ValidationException;
    ValidationSummary validate();
}
