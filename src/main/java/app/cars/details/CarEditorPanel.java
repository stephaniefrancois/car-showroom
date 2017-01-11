package app.cars.details;

import app.cars.CarEventArgs;
import app.objectComposition.ServiceLocator;
import app.styles.ComponentSizes;
import app.styles.LabelStyles;
import common.IRaiseEvents;
import common.ListenersManager;
import common.NumberExtensions;
import core.domain.car.CarMetadata;
import core.domain.car.CarProperties;
import core.domain.validation.ValidationSummary;
import core.stock.CarFactory;
import core.stock.CarFactoryProvider;
import core.stock.CarStock;
import data.CarMetadataRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

public final class CarEditorPanel extends JPanel implements
        IRaiseEvents<CarDetailsListener> {
    private final GridBagConstraints formGridConfig;
    private final JTextField makeField;
    private final JTextField modelField;
    private final JComboBox fuelTypeCombo;
    private final JComboBox transmissionCombo;
    private final JTextField numberOfSeatsField;
    private final JTextField priceField;
    private final JTextField colorField;
    private final JTextField mileageField;
    private final JTextField yearField;
    private final JButton saveButton;
    private final JButton cancelButton;

    private final ListenersManager<CarDetailsListener> listeners;
    private final CarStock carStock;
    private final CarFactoryProvider carFactoryProvider;
    private final JComboBox bodyStyleCombo;
    private final CarMetadataRepository carMetadata;
    private CarFactory carFactory;

    public CarEditorPanel() {
        setLayout(new GridBagLayout());

        this.listeners = new ListenersManager<>();
        this.carStock = ServiceLocator.getComposer().getCarStockService();
        this.carFactoryProvider = ServiceLocator.getComposer().getCarFactoryProvider();
        this.carFactory = this.carFactoryProvider.createCarFactory();
        this.carMetadata = ServiceLocator.getComposer().getCarMetadataRepository();

        formGridConfig = new GridBagConstraints();
        formGridConfig.fill = GridBagConstraints.NONE;

        makeField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        modelField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        yearField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        fuelTypeCombo = new JComboBox();
        bodyStyleCombo = new JComboBox();
        transmissionCombo = new JComboBox();
        numberOfSeatsField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        priceField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        colorField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);
        mileageField = new JTextField(ComponentSizes.INPUT_COLUMNS_COUNT);

        addControlWithLabel(makeField, "Make:", 0, 0);
        addControlWithLabel(modelField, "Model:", 1, 0);
        addControlWithLabel(yearField, "Year:", 2, 0);
        addControlWithLabel(colorField, "Color:", 3, 0);
        addControlWithLabel(fuelTypeCombo, "Fuel Type:", 4, 0);
        addControlWithLabel(bodyStyleCombo, "Body Style:", 5, 0);
        addControlWithLabel(transmissionCombo, "Transmission:", 6, 0);
        addControlWithLabel(numberOfSeatsField, "Number of seats:", 7, 0);
        addControlWithLabel(mileageField, "Mileage:", 8, 0);
        addControlWithLabel(priceField, "Price:", 9, 0);

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        buttonContainer.add(saveButton);
        buttonContainer.add(cancelButton);

        formGridConfig.gridy = 10;

        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        formGridConfig.gridx = 1;
        formGridConfig.anchor = GridBagConstraints.FIRST_LINE_START;
        formGridConfig.insets = new Insets(0, 0, 0, 0);
        add(buttonContainer, formGridConfig);

        this.configureButtonHandlers();
        this.populateCarMetadata();
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

    private void populateCarMetadata() {
        this.populateComboWithValues(this.fuelTypeCombo, () -> this.carMetadata.getFuelTypes());
        this.populateComboWithValues(this.bodyStyleCombo, () -> this.carMetadata.getBodyStyles());
        this.populateComboWithValues(this.transmissionCombo, () -> this.carMetadata.getTransmissions());
    }

    private void populateComboWithValues(JComboBox comboBox,
                                         Supplier<List<CarMetadata>> metadataProvider) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(
                metadataProvider.get().toArray());
        comboBox.setModel(model);
        comboBox.setSelectedIndex(0);
        comboBox.setEditable(false);
    }

    private void cancelCarEditing(ActionEvent e) {
        CarEventArgs args = new CarEventArgs(e.getSource(), carFactory.getCarId());
        listeners.notifyListeners(l -> l.carEditCancelled(args));
    }

    private void saveCar(ActionEvent e) {
        this.mapFormValuesToCarFactory();

        ValidationSummary validationSummary = carFactory.validate();
        if (validationSummary.getIsValid() == false) {
            // TODO: show validation errors
            System.err.println(String.format("Car with ID: '%d' is NOT valid!", carFactory.getCarId())); // TODO: log this
            return;
        }

        System.out.println(String.format("Car with ID: '%d' is valid ...", carFactory.getCarId()));

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
        carFactory.setMake(this.makeField.getText());
        carFactory.setModel(this.modelField.getText());
        carFactory.setYear(NumberExtensions.tryParseNumber(this.yearField.getText(), 0));
        carFactory.setColor(this.colorField.getText());
        carFactory.setMake(this.makeField.getText());
        carFactory.setFuelType((CarMetadata) this.fuelTypeCombo.getSelectedItem());
        carFactory.setBodyStyle((CarMetadata) this.bodyStyleCombo.getSelectedItem());
        carFactory.setTransmission((CarMetadata) this.transmissionCombo.getSelectedItem());
        carFactory.setNumberOfSeats(NumberExtensions.tryParseNumber(this.numberOfSeatsField.getText(), 0));
        carFactory.setMileage(NumberExtensions.tryParseNumber(this.mileageField.getText(), 0));
        carFactory.setPrice(NumberExtensions.tryParseNumber(this.priceField.getText(), new BigDecimal(0)));

        // TODO: map selected FEATURES from FORM to car factory!
    }

    public void createCar() {
        this.carFactory = this.carFactoryProvider.createCarFactory();
        setDefaultValuesForNewCar();
        this.mapCarValuesToForm(this.carFactory);
    }

    private void setDefaultValuesForNewCar() {
        this.fuelTypeCombo.setSelectedIndex(0);
        this.bodyStyleCombo.setSelectedIndex(0);
        this.transmissionCombo.setSelectedIndex(0);
        carFactory.setFuelType((CarMetadata) this.fuelTypeCombo.getSelectedItem());
        carFactory.setBodyStyle((CarMetadata) this.bodyStyleCombo.getSelectedItem());
        carFactory.setTransmission((CarMetadata) this.transmissionCombo.getSelectedItem());
    }

    public void editCar(int carId) {
        System.out.println("Editing car with id: " + carId); // TODO: add logging statement about car being edited
        CarProperties car = this.carStock.getCarDetails(carId);
        this.carFactory = this.carFactoryProvider.createCarFactory(car);
        mapCarValuesToForm(this.carFactory);
    }

    private void mapCarValuesToForm(CarProperties car) {
        this.makeField.setText(car.getMake());
        this.modelField.setText(car.getModel());
        this.yearField.setText(car.getYear().toString());
        this.colorField.setText(car.getColor());
        this.fuelTypeCombo.setSelectedItem(car.getFuelType());
        this.bodyStyleCombo.setSelectedItem(car.getBodyStyle());
        this.transmissionCombo.setSelectedItem(car.getTransmission());
        this.numberOfSeatsField.setText(car.getNumberOfSeats().toString());
        this.mileageField.setText(car.getMileage().toString());
        this.priceField.setText(car.getPrice().toString());

        // TODO: fill in Car FEATURES
        // List<CarFeature> features;
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
