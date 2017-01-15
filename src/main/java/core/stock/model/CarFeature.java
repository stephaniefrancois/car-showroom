package core.stock.model;

public final class CarFeature {
    private final int featureId;
    private final String description;

    public CarFeature(int featureId, String description) {
        this.featureId = featureId;
        this.description = description;
    }

    public int getFeatureId() {
        return featureId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarFeature that = (CarFeature) o;

        return getFeatureId() == that.getFeatureId() && getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getFeatureId();
        result = 31 * result + getDescription().hashCode();
        return result;
    }
}
