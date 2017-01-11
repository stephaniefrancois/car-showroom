package app.cars.details;

import app.cars.CarEventArgs;
import app.objectComposition.ServiceLocator;
import app.styles.ComponentSizes;
import app.styles.LabelStyles;
import common.IRaiseEvents;
import common.ListenersManager;
import core.domain.car.CarProperties;
import core.domain.validation.ValidationSummary;
import core.stock.CarFactory;
import core.stock.CarFactoryProvider;
import core.stock.CarStock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class CarEditorPanel extends JPanel implements
        IRaiseEvents<CarDetailsListener> {
    private final GridBagConstraints formGridConfig;
    private final JTextField makeField;
    private final JTextField modelField;
    private final JComboBox fuelTypeCombo;
    private final JComboBox transmissionCombo;
    private final JComboBox numberOfSeatsCombo;
    private final JTextField priceField;
    private final JTextField colorField;
    private final JTextField mileageField;
    private final JTextField yearField;
    private final JComboBox bodyStyle;
    private final JButton saveButton;
    private final JButton cancelButton;

    private final ListenersManager<CarDetailsListener> listeners;
    private final CarStock carStock;
    private final CarFactoryProvider carFactoryProvider;
    private CarFactory carFactory;

    // TODO: load car METADATA for editing

    public CarEditorPanel() {
        setLayout(new GridBagLayout());

        this.listeners = new ListenersManager<>();
        this.carStock = ServiceLocator.getComposer().getCarStockService();
        this.carFactoryProvider = ServiceLocator.getComposer().getCarFactoryProvider();
        this.carFactory = this.carFactoryProvider.createCarFactory();

        formGridConfig = new GridBagConstraints();
        formGridConfig.fill = GridBagConstraints.NONE;

        makeField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        modelField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        yearField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        fuelTypeCombo = new JComboBox();
        transmissionCombo = new JComboBox();
        numberOfSeatsCombo = new JComboBox();
        priceField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        colorField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        mileageField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        bodyStyle = new JComboBox();

        addControlWithLabel(makeField, "Make:", 0, 0);
        addControlWithLabel(yearField, "Year:", 1, 0);
        addControlWithLabel(fuelTypeCombo, "Fuel Type:", 2, 0);
        addControlWithLabel(transmissionCombo, "Transmission:", 3, 0);
        addControlWithLabel(priceField, "Price:", 4, 0);

        addControlWithLabel(modelField, "Model:", 0, 1);
        addControlWithLabel(colorField, "Color:", 1, 1);
        addControlWithLabel(bodyStyle, "Body Style:", 2, 1);
        addControlWithLabel(numberOfSeatsCombo, "Number of seats:", 3, 1);
        addControlWithLabel(mileageField, "Mileage:", 4, 1);

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        buttonContainer.add(saveButton);
        buttonContainer.add(cancelButton);

        formGridConfig.gridy = 6;

        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        formGridConfig.gridx = 1;
        formGridConfig.anchor = GridBagConstraints.FIRST_LINE_START;
        formGridConfig.insets = new Insets(0, 0, 0, 0);
        add(buttonContainer, formGridConfig);

        this.configureButtonHandlers();
    }

    private void addControlWithLabel(Component componentToAdd, String label, int rowIndex, int columnIndex) {
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
        add(componentToAdd, formGridConfig);
    }

    private void configureButtonHandlers() {
        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelCarEditing(e);
            }
        });

        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCar(e);
            }
        });
    }

    private void cancelCarEditing(ActionEvent e) {
        CarEventArgs args = new CarEventArgs(e.getSource(), carFactory.getCarId());
        listeners.notifyListeners(l -> l.carEditCancelled(args));
    }

    private void saveCar(ActionEvent e) {
        mapFormValuesToCarFactory();

        ValidationSummary validationSummary = carFactory.validate();
        if (validationSummary.getIsValid() == false) {
            // TODO: show validation errors
            System.err.println("Car is not valid!");
            return;
        }
        try {
            CarProperties car = carFactory.build();
            if (car.getCarId() == 0) {
                car = carStock.addCar(car);
            } else {
                carStock.updateCar(car);
            }
            CarEventArgs args = new CarEventArgs(e.getSource(), carFactory.getCarId());
            listeners.notifyListeners(l -> l.carSaved(args));
        } catch (Exception ex) {
            ex.printStackTrace(); // TODO: log error regarding car validation
        }
    }

    private void mapFormValuesToCarFactory() {
        // TODO: map Form Values To CarFactory!
    }

    public void editCar(int carId) {
        System.out.println("Editing car with id: " + carId); // TODO: add logging statement about car being edited
        CarProperties car = this.carStock.getCarDetails(carId);
        this.carFactory = this.carFactoryProvider.createCarFactory(car);
        mapCarFactoryValuesToForm();
    }

    private void mapCarFactoryValuesToForm() {
        // TODO: map CarFactory Values To Form
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
