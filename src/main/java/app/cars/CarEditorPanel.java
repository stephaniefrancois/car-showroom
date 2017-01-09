package app.cars;

import javax.swing.*;
import java.awt.*;

public final class CarEditorPanel extends JPanel {
    private final int DEFAULT_FIELD_COLUMNS_COUNT = 15;
    private final GridBagConstraints formGridConfig;
    private final Font defaultComponentFont;
    private final JTextField makeField;
    private final JTextField modelField;
    private final JComboBox fuelTypeCombo;
    private final JComboBox transmissionCombo;
    private final JComboBox numberOfSeatsCombo;
    private final JTextField priceField;
    private final JTextField colorField;
    private final JTextField mileageField;
    private final JTextField yearField;
    private final JButton okBtn;

    public CarEditorPanel() {
        Dimension dim = getPreferredSize();
        dim.width = 500;
        setPreferredSize(dim);

        setLayout(new GridBagLayout());

        defaultComponentFont = new Font("Arial", Font.PLAIN, 18);
        formGridConfig = new GridBagConstraints();
        formGridConfig.fill = GridBagConstraints.NONE;


        makeField = new JTextField(DEFAULT_FIELD_COLUMNS_COUNT);
        modelField = new JTextField(DEFAULT_FIELD_COLUMNS_COUNT);
        yearField = new JTextField(DEFAULT_FIELD_COLUMNS_COUNT);
        fuelTypeCombo = new JComboBox();
        transmissionCombo = new JComboBox();
        numberOfSeatsCombo = new JComboBox();
        priceField = new JTextField(DEFAULT_FIELD_COLUMNS_COUNT);
        colorField = new JTextField(DEFAULT_FIELD_COLUMNS_COUNT);
        mileageField = new JTextField(DEFAULT_FIELD_COLUMNS_COUNT);
        JComboBox bodyStyle = new JComboBox();


        okBtn = new JButton("OK");

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

        formGridConfig.gridy = 6;

        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        formGridConfig.gridx = 1;
        formGridConfig.anchor = GridBagConstraints.FIRST_LINE_START;
        formGridConfig.insets = new Insets(0, 0, 0, 0);
        add(okBtn, formGridConfig);


//         layoutComponents();


    }

    private void addControlWithLabel(Component componentToAdd, String label, int rowIndex, int columnIndex) {
        JLabel componentLabel = new JLabel(label);
        componentLabel.setLabelFor(componentToAdd);
        componentLabel.setFont(defaultComponentFont);
        componentToAdd.setFont(defaultComponentFont);

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
}
