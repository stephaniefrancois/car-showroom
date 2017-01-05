package testing.helpers;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class FakeModel {
    private final String data;
    private final BigDecimal value;
    private final LocalDate date;

    public FakeModel() {
        this.data = null;
        this.value = null;
        this.date = null;
    }

    public FakeModel(String data) {

        this.data = data;
        this.value = null;
        this.date = null;
    }

    public FakeModel(BigDecimal value) {

        this.data = null;
        this.value = value;
        this.date = null;
    }

    public FakeModel(LocalDate date) {

        this.data = null;
        this.value = null;
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public BigDecimal getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }
}
