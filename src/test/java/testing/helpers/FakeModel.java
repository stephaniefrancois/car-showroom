package testing.helpers;

import java.math.BigDecimal;

public final class FakeModel {
    private final String data;
    private final BigDecimal value;

    public FakeModel() {
        this.data = null;
        this.value = null;
    }

    public FakeModel(String data) {

        this.data = data;
        this.value = null;
    }

    public FakeModel(BigDecimal value) {

        this.data = null;
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public BigDecimal getValue() {
        return value;
    }
}
