package testing.helpers;

import core.validation.rules.MinRequiredValueRule;

import java.math.BigDecimal;

public final class MinRequiredFakeModelValueRule
        extends MinRequiredValueRule<FakeModel, BigDecimal> {
    public MinRequiredFakeModelValueRule(BigDecimal minAllowedValue) {
        super("data", minAllowedValue);
    }

    @Override
    protected BigDecimal getValueToValidate(FakeModel fakeModel) {
        return fakeModel.getValue();
    }
}
