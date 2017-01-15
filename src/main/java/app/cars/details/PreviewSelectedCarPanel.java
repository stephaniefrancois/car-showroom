package app.cars.details;

import app.cars.details.features.CarFeaturesPanel;
import app.common.ControlsHelper;
import app.common.details.ItemDetailsListener;
import app.common.details.PreviewSelectedItemPanel;
import app.objectComposition.ServiceLocator;
import app.styles.LabelStyles;
import common.ListenersManager;
import core.stock.CarStock;
import core.stock.model.CarDetails;

import javax.swing.*;
import java.awt.*;

public class PreviewSelectedCarPanel extends PreviewSelectedItemPanel<CarDetails> {

    private final GridBagConstraints formGridConfig;
    private final JLabel makeValueLabel;
    private final JLabel modelValueLabel;
    private final JLabel yearValueLabel;
    private final JLabel fuelTypeValueLabel;
    private final JLabel transmissionValueLabel;
    private final JLabel conditionValueLabel;
    private final JLabel colorValueLabel;
    private final JLabel bodyStyleValueLabel;
    private final JLabel mileageValueLabel;
    private final JLabel numberOfSeatsValueLabel;
    private final JLabel priceValueLabel;
    private final CarFeaturesPanel carFeaturesPanel;

    private final JButton editCarButton;
    private final ListenersManager<ItemDetailsListener> listeners;
    private final CarStock carStock;
    private final Insets controlsPadding;

    public PreviewSelectedCarPanel() {
        setLayout(new GridBagLayout());

        this.listeners = new ListenersManager<>();
        this.carStock = ServiceLocator.getComposer().getCarStockService();
        this.controlsPadding = new Insets(5, 0, 4, 5);

        this.formGridConfig = new GridBagConstraints();
        this.formGridConfig.fill = GridBagConstraints.NONE;

        this.makeValueLabel = new JLabel();
        this.modelValueLabel = new JLabel();
        this.yearValueLabel = new JLabel();
        this.fuelTypeValueLabel = new JLabel();
        this.transmissionValueLabel = new JLabel();
        this.conditionValueLabel = new JLabel();
        this.colorValueLabel = new JLabel();
        this.bodyStyleValueLabel = new JLabel();
        this.mileageValueLabel = new JLabel();
        this.numberOfSeatsValueLabel = new JLabel();
        this.priceValueLabel = new JLabel();
        this.carFeaturesPanel = new CarFeaturesPanel();

        this.carFeaturesPanel.setBackground(new JLabel().getBackground());
        this.editCarButton = new JButton("Edit car ...");

        this.addControlWithLabel(makeValueLabel, "Make:", 0);
        this.addControlWithLabel(modelValueLabel, "Model:", 1);
        this.addControlWithLabel(yearValueLabel, "Year:", 2);
        this.addControlWithLabel(fuelTypeValueLabel, "Fuel Type:", 3);
        this.addControlWithLabel(transmissionValueLabel, "Transmission:", 4);
        this.addControlWithLabel(conditionValueLabel, "Condition:", 5);
        this.addControlWithLabel(colorValueLabel, "Color:", 6);
        this.addControlWithLabel(bodyStyleValueLabel, "Body style:", 7);
        this.addControlWithLabel(mileageValueLabel, "Mileage:", 8);
        this.addControlWithLabel(numberOfSeatsValueLabel, "Number of seats:", 9);
        this.addControlWithLabel(priceValueLabel, "Price:", 10);
        addCarFeaturesLabels(carFeaturesPanel);


        formGridConfig.gridy = 11;

        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        formGridConfig.gridx = 1;
        formGridConfig.anchor = GridBagConstraints.FIRST_LINE_START;
        formGridConfig.insets = new Insets(0, 0, 0, 0);
        add(editCarButton, formGridConfig);

        editCarButton.addActionListener(e -> {
            this.editItem(e.getSource());
        });
    }

    @Override
    protected CarDetails getItem(int id) {
        return this.carStock.getCarDetails(id);
    }

    @Override
    protected void populateItemInformation(CarDetails item) {
        makeValueLabel.setText(item.getMake());
        modelValueLabel.setText(item.getModel());
        yearValueLabel.setText(item.getYear().toString());
        fuelTypeValueLabel.setText(item.getFuelType().toString());
        transmissionValueLabel.setText(item.getTransmission().toString());
        conditionValueLabel.setText(item.getCondition().getDescription());
        colorValueLabel.setText(item.getColor());
        bodyStyleValueLabel.setText(item.getBodyStyle().toString());
        mileageValueLabel.setText(item.getMileage().toString());
        numberOfSeatsValueLabel.setText(item.getNumberOfSeats().toString());
        priceValueLabel.setText(item.getPrice().toString());
        carFeaturesPanel.displayCarFeatures(item.getFeatures());
    }

    private void addControlWithLabel(Component componentToAdd,
                                     String label,
                                     int rowIndex) {
        ControlsHelper.addControlWithLabel(componentToAdd, label, rowIndex, this.controlsPadding, this.formGridConfig, this);
    }

    private void addCarFeaturesLabels(CarFeaturesPanel carFeaturesPanel) {
        final int rowsToSpan = 10;

        JLabel componentLabel = new JLabel("Features:");
        componentLabel.setLabelFor(carFeaturesPanel);
        componentLabel.setFont(LabelStyles.getFontForFieldLabel());
        carFeaturesPanel.setFont(LabelStyles.getFontForFieldLabel());

        formGridConfig.gridy = 0;
        formGridConfig.weightx = 0.4;
        formGridConfig.weighty = 0.1;
        formGridConfig.gridx = 2;

        formGridConfig.fill = GridBagConstraints.NONE;
        formGridConfig.anchor = GridBagConstraints.LINE_START;
        formGridConfig.insets = this.controlsPadding;
        add(componentLabel, formGridConfig);

        formGridConfig.gridy = 1;
        formGridConfig.gridx = 2;
        formGridConfig.weighty = 1;
        formGridConfig.insets = this.controlsPadding;
        formGridConfig.anchor = GridBagConstraints.NORTHWEST;
        formGridConfig.gridheight = rowsToSpan;
        add(carFeaturesPanel, formGridConfig);
    }
}
