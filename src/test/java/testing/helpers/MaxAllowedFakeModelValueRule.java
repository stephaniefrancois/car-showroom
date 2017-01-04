package testing.helpers;

import core.validation.rules.MaxAllowedValueRule;

import java.math.BigDecimal;

public final class MaxAllowedFakeModelValueRule
        extends MaxAllowedValueRule<FakeModel, BigDecimal> {
    public MaxAllowedFakeModelValueRule(BigDecimal maxAllowedValue) {
        super("data", maxAllowedValue);
    }

    @Override
    protected BigDecimal getValueToValidate(FakeModel fakeModel) {
        return fakeModel.getValue();
    }
}
