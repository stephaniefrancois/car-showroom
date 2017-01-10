package core.domain.car;

public abstract class Condition {
    public abstract String getDescription();

    @Override
    public String toString() {
        return getDescription();
    }
}
