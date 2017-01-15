package core.stock.model;

public abstract class Condition {
    public abstract String getDescription();

    @Override
    public String toString() {
        return getDescription();
    }
}
