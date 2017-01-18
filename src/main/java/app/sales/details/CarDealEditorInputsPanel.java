package app.sales.details;

import app.common.details.EditorInputsPanel;
import app.common.validation.ValidateAbleFieldDescriptor;
import app.styles.BorderStyles;
import core.deal.CarDealFactory;
import core.deal.model.CarDealDetails;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

// TODO: ditch this! :)
public final class CarDealEditorInputsPanel extends EditorInputsPanel<CarDealDetails, CarDealFactory> {

    //   private final GridBagConstraints formGridConfig;
    //    private final JTextField firstNameField;
//    private final JTextField lastNameField;
//    private final JTextField cityField;
    //  private final Insets controlsPadding;

    private Map<String, ValidateAbleFieldDescriptor> fieldsMap;

    public CarDealEditorInputsPanel() {
        setLayout(new BorderLayout());
        CarDealWizardHeaderPanel header = new CarDealWizardHeaderPanel("We will make you a great deal!");


        JLabel contentLabel = new JLabel("Content");

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        navigationPanel.setBorder(BorderStyles.getContentMargin());
        JButton goBackButton = new JButton("< Previous");
        JButton goForwardButton = new JButton("Next >");
        JButton finishButton = new JButton("Finish!");

        navigationPanel.add(goBackButton);
        navigationPanel.add(goForwardButton);
        navigationPanel.add(finishButton);

        add(header, BorderLayout.NORTH);
        add(contentLabel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);

//        this.controlsPadding = new Insets(5, 0, 4, 5);
//        this.fieldsMap = new HashMap<>();
//        formGridConfig = new GridBagConstraints();
//        formGridConfig.fill = GridBagConstraints.NONE;

//        firstNameField = new JTextField();
//        lastNameField = new JTextField();
//        cityField = new JTextField();
//
//        addControlWithLabel(firstNameField, "First Name", 0);
//        addControlWithLabel(lastNameField, "Last Name", 1);
//        addControlWithLabel(cityField, "City", 2);
//
//        formGridConfig.gridy = 10;
//        formGridConfig.weightx = 1;
//        formGridConfig.weighty = 2.0;
//
//        JPanel placeholder = new JPanel();
//        add(placeholder, formGridConfig);
    }


    public Map<String, ValidateAbleFieldDescriptor> getFieldsMap() {
        return fieldsMap;
    }

    @Override
    public CarDealFactory mapFormValuesToItemFactory(CarDealFactory itemFactory) {


//        itemFactory.setFirstName(this.firstNameField.getText());
//        itemFactory.setLastName(this.lastNameField.getText());
//        itemFactory.setCity(this.cityField.getText());
        return itemFactory;


    }

    @Override
    public CarDealFactory setDefaultValuesForNewItem(CarDealFactory itemFactory) {
        return itemFactory;
    }

    @Override
    public void mapItemValuesToForm(CarDealFactory item) {
//        this.firstNameField.setText(item.getFirstName());
//        this.lastNameField.setText(item.getLastName());
//        this.cityField.setText(item.getCity());
    }

//    private void addControlWithLabel(Component componentToAdd, String label, int rowIndex) {
//        JLabel componentLabel = new JLabel(String.format("%s:", label));
//        componentLabel.setLabelFor(componentToAdd);
//        componentLabel.setFont(LabelStyles.getFontForFieldLabel());
//        componentToAdd.setFont(LabelStyles.getFontForFieldLabel());
//
//        formGridConfig.gridy = rowIndex;
//        formGridConfig.gridx = 0;
//        formGridConfig.weightx = 0.1;
//        formGridConfig.weighty = 0.1;
//
//        formGridConfig.fill = GridBagConstraints.NONE;
//        formGridConfig.anchor = GridBagConstraints.LINE_END;
//        formGridConfig.insets = this.controlsPadding;
//        add(componentLabel, formGridConfig);
//
//        formGridConfig.gridx = 1;
//        formGridConfig.weightx = 0.4;
//        formGridConfig.insets = this.controlsPadding;
//        formGridConfig.anchor = GridBagConstraints.LINE_START;
//        formGridConfig.fill = GridBagConstraints.HORIZONTAL;
//        add(componentToAdd, formGridConfig);
//        this.fieldsMap.put(label, new ValidateAbleFieldDescriptor(componentLabel, componentToAdd));
//    }
}
