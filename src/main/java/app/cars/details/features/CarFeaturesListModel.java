package app.cars.details.features;

import core.domain.car.CarFeature;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.List;
import java.util.Objects;

public final class CarFeaturesListModel implements ListModel<CarFeature> {
    private final List<CarFeature> data;

    public CarFeaturesListModel(List<CarFeature> data) {
        Objects.requireNonNull(data);
        this.data = data;
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public CarFeature getElementAt(int index) {
        return data.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
