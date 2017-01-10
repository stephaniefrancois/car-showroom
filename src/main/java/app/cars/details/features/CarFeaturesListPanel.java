package app.cars.details.features;

import core.domain.car.CarFeature;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;


public final class CarFeaturesListPanel extends JPanel {
    private final JList<CarFeature> featuresList;

    public CarFeaturesListPanel() {
        setLayout(new BorderLayout());
        featuresList = new JList<CarFeature>();
        add(featuresList, BorderLayout.CENTER);
    }

    public void displayCarFeatures(List<CarFeature> features) {
        Objects.requireNonNull(features);
        featuresList.removeAll();
        featuresList.setModel(new CarFeaturesListModel(features));
    }
}
