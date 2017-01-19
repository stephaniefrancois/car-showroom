package app.cars.details;

import app.cars.details.features.CarFeaturesEditorPanel;
import app.common.details.EditorInputsPanel;
import app.common.validation.ValidateAbleFieldDescriptor;
import app.styles.LabelStyles;
import common.NumberExtensions;
import composition.ServiceLocator;
import core.stock.CarFactory;
import core.stock.model.CarDetails;
import core.stock.model.CarFeature;
import core.stock.model.CarMetadata;
import data.CarMetadataRepository;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class CarEditorInputsPanel extends EditorInputsPanel<CarDetails, CarFactory> {

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
    private final Insets controlsPadding;
    private final CarFeaturesEditorPanel carFeaturesEditorPanel;

    private Map<String, ValidateAbleFieldDescriptor> fieldsMap;

    public CarEditorInputsPanel() {
        setLayout(new GridBagLayout());

        this.carMetadata = ServiceLocator.getComposer().getCarMetadataRepository();
        this.controlsPadding = new Insets(5, 0, 4, 5);
        this.fieldsMap = new HashMap<>();
        formGridConfig = new GridBagConstraints();
        formGridConfig.fill = GridBagConstraints.NONE;

        makeField = new JTextField();
        modelField = new JTextField();
        yearField = new JTextField();
        fuelTypeCombo = new JComboBox();
        bodyStyleCombo = new JComboBox();
        transmissionCombo = new JComboBox();
        numberOfSeatsField = new JTextField();
        priceField = new JTextField();
        colorField = new JTextField();
        mileageField = new JTextField();
        this.carFeaturesEditorPanel = new CarFeaturesEditorPanel();

        addControlWithLabel(makeField, "Make", 0);
        addControlWithLabel(modelField, "Model", 1);
        addControlWithLabel(yearField, "Year", 2);
        addControlWithLabel(colorField, "Color", 3);
        addControlWithLabel(fuelTypeCombo, "Fuel Type", 4);
        addControlWithLabel(bodyStyleCombo, "Body Style", 5);
        addControlWithLabel(transmissionCombo, "Transmission", 6);
        addControlWithLabel(numberOfSeatsField, "Number of seats", 7);
        addControlWithLabel(mileageField, "Mileage", 8);
        addControlWithLabel(priceField, "Price", 9);

        formGridConfig.gridy = 10;
        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        JPanel placeholder = new JPanel();
        add(placeholder, formGridConfig);

        addCarFeaturesLabels(carFeaturesEditorPanel);

        this.populateCarMetadata();
    }

    @Override
    public Map<String, ValidateAbleFieldDescriptor> getFieldsMap() {
        return fieldsMap;
    }

    private void addControlWithLabel(Component componentToAdd, String label, int rowIndex) {
        JLabel componentLabel = new JLabel(String.format("%s:", label));
        componentLabel.setLabelFor(componentToAdd);
        componentLabel.setFont(LabelStyles.getFontForFieldLabel());
        componentToAdd.setFont(LabelStyles.getFontForFieldLabel());

        formGridConfig.gridy = rowIndex;
        formGridConfig.gridx = 0;
        formGridConfig.weightx = 0.1;
        formGridConfig.weighty = 0.1;

        formGridConfig.fill = GridBagConstraints.NONE;
        formGridConfig.anchor = GridBagConstraints.LINE_END;
        formGridConfig.insets = this.controlsPadding;
        add(componentLabel, formGridConfig);

        formGridConfig.gridx = 1;
        formGridConfig.weightx = 0.4;
        formGridConfig.insets = this.controlsPadding;
        formGridConfig.anchor = GridBagConstraints.LINE_START;
        formGridConfig.fill = GridBagConstraints.HORIZONTAL;
        add(componentToAdd, formGridConfig);
        this.fieldsMap.put(label, new ValidateAbleFieldDescriptor(componentLabel, componentToAdd));
    }

    private void addCarFeaturesLabels(CarFeaturesEditorPanel carFeaturesEditorPanel) {
        final int rowsToSpan = 9;

        JLabel componentLabel = new JLabel("Features:");
        componentLabel.setLabelFor(carFeaturesEditorPanel);
        componentLabel.setFont(LabelStyles.getFontForFieldLabel());
        carFeaturesEditorPanel.setFont(LabelStyles.getFontForFieldLabel());

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
        formGridConfig.fill = GridBagConstraints.VERTICAL;
        add(carFeaturesEditorPanel, formGridConfig);
    }


    private void populateCarMetadata() {
        this.populateComboWithValues(this.fuelTypeCombo, this.carMetadata::getFuelTypes);
        this.populateComboWithValues(this.bodyStyleCombo, this.carMetadata::getBodyStyles);
        this.populateComboWithValues(this.transmissionCombo, this.carMetadata::getTransmissions);
        this.carFeaturesEditorPanel.displayAvailableCarFeatures(this.carMetadata.getFeatures());
    }

    private void populateComboWithValues(JComboBox comboBox,
                                         Supplier<List<CarMetadata>> metadataProvider) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(
                metadataProvider.get().toArray());
        comboBox.setModel(model);
        comboBox.setSelectedIndex(0);
        comboBox.setEditable(false);
    }

    @Override
    public CarFactory mapFormValuesToItemFactory(CarFactory carFactory) {
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

        carFactory.removeAllFeatures();
        List<CarFeature> features = this.carFeaturesEditorPanel.getSelectedCarFeatures();
        features.forEach(carFactory::addCarFeature);

        return carFactory;
    }

    @Override
    public CarFactory setDefaultValuesForNewItem(CarFactory carFactory) {
        carFactory.setFuelType((CarMetadata) this.fuelTypeCombo.getItemAt(0));
        carFactory.setBodyStyle((CarMetadata) this.bodyStyleCombo.getItemAt(0));
        carFactory.setTransmission((CarMetadata) this.transmissionCombo.getItemAt(0));

        return carFactory;
    }

    @Override
    public void mapItemValuesToForm(CarFactory item) {
        this.makeField.setText(item.getMake());
        this.modelField.setText(item.getModel());
        this.yearField.setText(item.getYear().toString());
        this.colorField.setText(item.getColor());
        this.fuelTypeCombo.setSelectedItem(item.getFuelType());
        this.bodyStyleCombo.setSelectedItem(item.getBodyStyle());
        this.transmissionCombo.setSelectedItem(item.getTransmission());
        this.numberOfSeatsField.setText(item.getNumberOfSeats().toString());
        this.mileageField.setText(item.getMileage().toString());
        this.priceField.setText(item.getPrice().toString());
        this.carFeaturesEditorPanel.displaySelectedCarFeatures(item.getFeatures());
    }
}
