package testing.helpers;

import core.validation.rules.MinRequiredLengthRule;

public final class MinRequiredDataLengthRule extends MinRequiredLengthRule<FakeModel> {
    public MinRequiredDataLengthRule(int minRequiredLength) {
        super("data", minRequiredLength);
    }

    @Override
    protected String getValueToValidate(FakeModel fakeModel) {
        return fakeModel.getData();
    }
}
