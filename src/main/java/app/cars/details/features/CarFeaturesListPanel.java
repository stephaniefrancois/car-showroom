package app.cars.details.features;

import app.styles.BorderStyles;
import app.styles.LabelStyles;
import common.StringExtensions;
import core.domain.car.CarFeature;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;


public final class CarFeaturesListPanel extends JPanel {
    private final JTextArea featuresLabel;

    public CarFeaturesListPanel() {
        setLayout(new BorderLayout());
        this.featuresLabel = new JTextArea();
        this.featuresLabel.setBackground(LabelStyles.getBackgroudColorForFieldLabel());
        this.featuresLabel.setFont(LabelStyles.getFontForFieldLabel());
        this.featuresLabel.setLineWrap(true);
        this.featuresLabel.setEditable(false);
        add(this.featuresLabel, BorderLayout.CENTER);
    }

    public void displayCarFeatures(List<CarFeature> features) {
        Objects.requireNonNull(features);
        StringBuilder sb = new StringBuilder();
        features.forEach(f -> {
            sb.append(String.format("- %s", f.getDescription()));
            sb.append(StringExtensions.NewLineSeparator);
            sb.append(StringExtensions.NewLineSeparator);
        });
        this.featuresLabel.setText(sb.toString());
    }
}
