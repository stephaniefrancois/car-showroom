package testing.helpers;

import core.validation.rules.StringNotEmptyRule;

public final class DataIsNotEmptyRule extends StringNotEmptyRule<FakeModel> {
    public DataIsNotEmptyRule() {
        super("data");
    }

    @Override
    protected String getValueToValidate(FakeModel fakeModel) {
        return fakeModel.getData();
    }
}
