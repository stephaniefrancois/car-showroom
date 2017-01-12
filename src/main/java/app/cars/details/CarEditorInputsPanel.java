package app.cars.details;

import app.objectComposition.ServiceLocator;
import app.styles.ComponentSizes;
import app.styles.LabelStyles;
import common.NumberExtensions;
import core.domain.car.CarMetadata;
import core.domain.car.CarProperties;
import core.stock.CarFactory;
import data.CarMetadataRepository;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

public final class CarEditorInputsPanel extends JPanel {

    private final GridBagConstraints formGridConfig;
    private final JTextField makeField;
    private final JTextField modelField;
    private final JComboBox fuelTypeCombo;
    private final JComboBox bodyStyleCombo;
    private final JComboBox transmissionCombo;
    private final JTextField numberOfSeatsField;
    private final JTextField priceField;
    private final JTextField colorField;
    private final JTextField mileageField;
    private final JTextField yearField;

    private final CarMetadataRepository carMetadata;

    public CarEditorInputsPanel() {
        setLayout(new GridBagLayout());

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

        formGridConfig.gridy = 10;
        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        JPanel placeholder = new JPanel();
        add(placeholder, formGridConfig);

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

    public CarFactory mapFormValuesToCarFactory(CarFactory carFactory) {
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

        return carFactory;
    }

    public CarFactory setDefaultValuesForNewCar(CarFactory carFactory) {
        carFactory.setFuelType((CarMetadata) this.fuelTypeCombo.getItemAt(0));
        carFactory.setBodyStyle((CarMetadata) this.bodyStyleCombo.getItemAt(0));
        carFactory.setTransmission((CarMetadata) this.transmissionCombo.getItemAt(0));

        return carFactory;
    }

    public void mapCarValuesToForm(CarProperties car) {
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
}
