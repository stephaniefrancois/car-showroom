package app.cars.details;

import app.cars.CarEventArgs;
import app.cars.details.features.CarFeaturesPanel;
import app.objectComposition.ServiceLocator;
import app.styles.LabelStyles;
import common.IRaiseEvents;
import core.domain.car.CarProperties;
import core.stock.CarStock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
    private final List<CarDetailsListener> listeners;
    private final CarStock carStock;
    private CarProperties car;

    public PreviewSelectedCarPanel() {
        setLayout(new GridBagLayout());

        this.car = null;
        this.listeners = new ArrayList<>();
        this.carStock = ServiceLocator.getComposer().getCarStockService();


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

        addControlWithLabel(makeValueLabel, "Make:", 0, 0);
        addControlWithLabel(modelValueLabel, "Model:", 1, 0);
        addControlWithLabel(yearValueLabel, "Year:", 2, 0);
        addControlWithLabel(fuelTypeValueLabel, "Fuel Type:", 3, 0);
        addControlWithLabel(transmissionValueLabel, "Transmission:", 4, 0);
        addControlWithLabel(conditionValueLabel, "Condition:", 5, 0);
        addControlWithLabel(colorValueLabel, "Color:", 6, 0);
        addControlWithLabel(bodyStyleValueLabel, "Body style:", 7, 0);
        addControlWithLabel(mileageValueLabel, "Mileage:", 8, 0);
        addControlWithLabel(numberOfSeatsValueLabel, "Number of seats:", 9, 0);
        addControlWithLabel(priceValueLabel, "Price:", 10, 0);
        addControlWithLabel(carFeaturesPanel, "Features:", 0, 1, 11);

        formGridConfig.gridy = 11;

        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        formGridConfig.gridx = 1;
        formGridConfig.anchor = GridBagConstraints.FIRST_LINE_START;
        formGridConfig.insets = new Insets(0, 0, 0, 0);
        add(editCarBtn, formGridConfig);

        editCarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyListenersAboutCarEditRequest(e.getSource());
            }
        });
    }

    private void addControlWithLabel(Component componentToAdd,
                                     String label,
                                     int rowIndex,
                                     int columnIndex) {
        addControlWithLabel(componentToAdd, label, rowIndex, columnIndex, 1);
    }

    private void addControlWithLabel(Component componentToAdd,
                                     String label,
                                     int rowIndex,
                                     int columnIndex,
                                     int spanNumberOfRows) {

        JLabel componentLabel = new JLabel(label);
        componentLabel.setLabelFor(componentToAdd);
        componentLabel.setFont(LabelStyles.getFontForFieldLabel());
        componentToAdd.setFont(LabelStyles.getFontForFieldLabel());

        int columnMultiplier = 2;

        formGridConfig.gridy = rowIndex;
        formGridConfig.weightx = 1;
        formGridConfig.weighty = 0.1;
        formGridConfig.gridx = columnIndex * columnMultiplier;

        formGridConfig.fill = GridBagConstraints.NONE;
        formGridConfig.anchor = GridBagConstraints.LINE_END;
        formGridConfig.insets = new Insets(5, 0, 5, 10);
        add(componentLabel, formGridConfig);

        formGridConfig.gridx = columnIndex * columnMultiplier + 1;
        formGridConfig.insets = new Insets(5, 0, 5, 0);
        formGridConfig.anchor = GridBagConstraints.LINE_START;
        formGridConfig.gridheight = spanNumberOfRows;
        add(componentToAdd, formGridConfig);
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
        fuelTypeValueLabel.setText(car.getFuelType().getDescription());
        transmissionValueLabel.setText(car.getTransmission().getDescription());
        conditionValueLabel.setText(car.getCondition().getDescription());
        colorValueLabel.setText(car.getColor());
        bodyStyleValueLabel.setText(car.getBodyStyle().getDescription());
        mileageValueLabel.setText(car.getMileage().toString());
        numberOfSeatsValueLabel.setText(car.getNumberOfSeats().toString());
        priceValueLabel.setText(car.getPrice().toString());
        carFeaturesPanel.displayCarFeatures(car.getFeatures());
    }

    @Override
    public void addListener(CarDetailsListener listenerToAdd) {
        if (!listeners.contains(listenerToAdd)) {
            listeners.add(listenerToAdd);
        }
    }

    @Override
    public void removeListener(CarDetailsListener listenerToRemove) {
        if (listeners.contains(listenerToRemove)) {
            listeners.remove(listenerToRemove);
        }
    }


    private void notifyListenersAboutCarEditRequest(Object source) {
        if (this.car == null) {
            return;
        }

        if (listeners.size() > 0) {
            CarEventArgs event = new CarEventArgs(source, this.car.getCarId());
            listeners.forEach(listener -> listener.carEditRequested(event));
        }
    }
}