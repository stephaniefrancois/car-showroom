package app.cars.details.features;

import core.domain.car.CarFeature;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CarFeaturesPanel extends JPanel {

    private final CardLayout contentPresenter;
    private Map<String, JPanel> cards;

    public CarFeaturesPanel() {
        contentPresenter = new CardLayout();
        setLayout(contentPresenter);
        cards = configureContentPages();
        cards.forEach((key, panel) -> {
            add(panel, key);
        });
    }

    private Map<String, JPanel> configureContentPages() {
        Map<String, JPanel> cards = new HashMap<>();
        cards.put(CarFeaturesListPanel.class.getName(), new CarFeaturesListPanel());
        cards.put(NoCarFeaturesPanel.class.getName(), new NoCarFeaturesPanel());
        return cards;
    }

    public void displayCarFeatures(List<CarFeature> carFeaturesToDisplay) {
        if (carFeaturesToDisplay == null || carFeaturesToDisplay.isEmpty()) {
            contentPresenter.show(this, NoCarFeaturesPanel.class.getName());
        } else {
            CarFeaturesListPanel featuresPanel =
                    (CarFeaturesListPanel) this.cards.get(CarFeaturesListPanel.class.getName());
            featuresPanel.displayCarFeatures(carFeaturesToDisplay);
            contentPresenter.show(this, CarFeaturesListPanel.class.getName());
        }
    }
}
