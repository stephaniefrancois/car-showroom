package testing.helpers;

import core.validation.rules.MaxAllowedLengthRule;

public final class MaxAllowedDataLengthRule extends MaxAllowedLengthRule<FakeModel> {
    public MaxAllowedDataLengthRule(int maxAllowedLength) {
        super("data", maxAllowedLength);
    }

    @Override
    protected String getValueToValidate(FakeModel fakeModel) {
        return fakeModel.getData();
    }
}
