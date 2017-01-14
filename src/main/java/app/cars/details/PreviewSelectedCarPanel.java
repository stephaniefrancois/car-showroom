package app.cars.details;

import app.cars.CarEventArgs;
import app.cars.details.features.CarFeaturesPanel;
import app.objectComposition.ServiceLocator;
import app.styles.LabelStyles;
import common.IRaiseEvents;
import common.ListenersManager;
import core.domain.car.CarProperties;
import core.stock.CarStock;

import javax.swing.*;
import java.awt.*;

public class PreviewSelectedCarPanel extends JPanel implements IRaiseEvents<CarDetailsListener> {

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

    private final JButton editCarBtn;
    private final ListenersManager<CarDetailsListener> listeners;
    private final CarStock carStock;
    private final Insets controlsPadding;
    private CarProperties car;

    public PreviewSelectedCarPanel() {
        setLayout(new GridBagLayout());

        this.car = null;
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
        this.editCarBtn = new JButton("Edit car ...");

        addControlWithLabel(makeValueLabel, "Make:", 0);
        addControlWithLabel(modelValueLabel, "Model:", 1);
        addControlWithLabel(yearValueLabel, "Year:", 2);
        addControlWithLabel(fuelTypeValueLabel, "Fuel Type:", 3);
        addControlWithLabel(transmissionValueLabel, "Transmission:", 4);
        addControlWithLabel(conditionValueLabel, "Condition:", 5);
        addControlWithLabel(colorValueLabel, "Color:", 6);
        addControlWithLabel(bodyStyleValueLabel, "Body style:", 7);
        addControlWithLabel(mileageValueLabel, "Mileage:", 8);
        addControlWithLabel(numberOfSeatsValueLabel, "Number of seats:", 9);
        addControlWithLabel(priceValueLabel, "Price:", 10);
        addCarFeaturesLabels(carFeaturesPanel);


        formGridConfig.gridy = 11;

        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        formGridConfig.gridx = 1;
        formGridConfig.anchor = GridBagConstraints.FIRST_LINE_START;
        formGridConfig.insets = new Insets(0, 0, 0, 0);
        add(editCarBtn, formGridConfig);

        editCarBtn.addActionListener(e -> {
            if (car == null) {
                return;
            }
            CarEventArgs event = new CarEventArgs(e.getSource(), car.getCarId());
            listeners.notifyListeners(l -> l.carEditRequested(event));
        });
    }

    private void addControlWithLabel(Component componentToAdd,
                                     String label,
                                     int rowIndex) {
        addControlWithLabel(componentToAdd, label, rowIndex, 0);
    }

    private void addControlWithLabel(Component componentToAdd,
                                     String label,
                                     int rowIndex,
                                     int columnIndex) {

        JLabel componentLabel = new JLabel(label);
        componentLabel.setLabelFor(componentToAdd);
        componentLabel.setFont(LabelStyles.getFontForFieldLabel());
        componentToAdd.setFont(LabelStyles.getFontForFieldLabel());

        int columnMultiplier = 2;

        formGridConfig.gridy = rowIndex;
        formGridConfig.gridx = columnIndex * columnMultiplier;
        formGridConfig.weightx = 0.2;
        formGridConfig.weighty = 0.1;

        formGridConfig.fill = GridBagConstraints.NONE;
        formGridConfig.anchor = GridBagConstraints.LINE_END;
        formGridConfig.insets = this.controlsPadding;
        add(componentLabel, formGridConfig);

        formGridConfig.gridx = columnIndex * columnMultiplier + 1;
        formGridConfig.weightx = 0.4;
        formGridConfig.insets = this.controlsPadding;
        formGridConfig.anchor = GridBagConstraints.LINE_START;
        formGridConfig.gridheight = 1;
        add(componentToAdd, formGridConfig);
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

    public void previewCar(int carId) {
        CarProperties car = this.carStock.getCarDetails(carId);
        if (car == null) {
            this.car = null;
            // TODO: log a warning that car was not found
            // TODO: RAISE event to tell that car was not found and that NOT FOUND screen would be displayed
            return;
        }

        this.car = car;
        populateCarInformation(car);
    }

    private void populateCarInformation(CarProperties car) {
        makeValueLabel.setText(car.getMake());
        modelValueLabel.setText(car.getModel());
        yearValueLabel.setText(car.getYear().toString());
        fuelTypeValueLabel.setText(car.getFuelType().toString());
        transmissionValueLabel.setText(car.getTransmission().toString());
        conditionValueLabel.setText(car.getCondition().getDescription());
        colorValueLabel.setText(car.getColor());
        bodyStyleValueLabel.setText(car.getBodyStyle().toString());
        mileageValueLabel.setText(car.getMileage().toString());
        numberOfSeatsValueLabel.setText(car.getNumberOfSeats().toString());
        priceValueLabel.setText(car.getPrice().toString());
        carFeaturesPanel.displayCarFeatures(car.getFeatures());
    }

    @Override
    public void addListener(CarDetailsListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(CarDetailsListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }
}
