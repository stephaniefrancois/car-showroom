package testing.helpers;

import core.validation.rules.MandatoryFieldRule;

public final class DataIsMandatoryRule extends MandatoryFieldRule<FakeModel, String> {
    public DataIsMandatoryRule() {
        super("data");
    }

    @Override
    protected String getValueToValidate(FakeModel fakeModel) {
        return fakeModel.getData();
    }
}
