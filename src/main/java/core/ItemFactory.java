package core;

import core.domain.IHaveIdentifier;
import core.domain.validation.ValidationException;
import core.domain.validation.ValidationSummary;

public interface ItemFactory<TModel> extends IHaveIdentifier {
    TModel build() throws ValidationException;
    ValidationSummary validate();
}
