package app.customers.details;

import app.common.ValidateAbleFieldDescriptor;
import app.styles.LabelStyles;
import core.customer.CustomerFactory;
import core.domain.deal.CustomerProperties;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class CustomerEditorInputsPanel extends JPanel {

    private final GridBagConstraints formGridConfig;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField cityField;
    private final Insets controlsPadding;

    private Map<String, ValidateAbleFieldDescriptor> fieldsMap;

    public CustomerEditorInputsPanel() {
        setLayout(new GridBagLayout());

        this.controlsPadding = new Insets(5, 0, 4, 5);
        this.fieldsMap = new HashMap<>();
        formGridConfig = new GridBagConstraints();
        formGridConfig.fill = GridBagConstraints.NONE;

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        cityField = new JTextField();

        addControlWithLabel(firstNameField, "First Name", 0);
        addControlWithLabel(lastNameField, "Last Name", 1);
        addControlWithLabel(cityField, "City", 2);

        formGridConfig.gridy = 10;
        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        JPanel placeholder = new JPanel();
        add(placeholder, formGridConfig);
    }

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

    public CustomerFactory mapFormValuesToCustomerFactory(CustomerFactory customerFactory) {
        customerFactory.setFirstName(this.firstNameField.getText());
        customerFactory.setLastName(this.lastNameField.getText());
        customerFactory.setCity(this.cityField.getText());
        return customerFactory;
    }

    public void mapCustomerValuesToForm(CustomerProperties customer) {
        this.firstNameField.setText(customer.getFirstName());
        this.lastNameField.setText(customer.getLastName());
        this.cityField.setText(customer.getCity());
    }
}
