package app.customers.details;

import app.common.ControlsHelper;
import app.common.details.PreviewSelectedItemPanel;
import composition.ServiceLocator;
import core.customer.model.Customer;
import data.CustomerRepository;

import javax.swing.*;
import java.awt.*;

public class PreviewSelectedCustomerPanel extends PreviewSelectedItemPanel<Customer> {

    private final GridBagConstraints formGridConfig;
    private final JLabel firstNameValueLabel;
    private final JLabel lastNameValueLabel;
    private final JLabel cityValueLabel;
    private final JLabel customerSinceValueLabel;

    private final JButton editCustomerBtn;
    private final CustomerRepository customerRepository;
    private final Insets controlsPadding;

    public PreviewSelectedCustomerPanel() {
        setLayout(new GridBagLayout());

        this.customerRepository = ServiceLocator.getComposer().getCustomerRepository();
        this.controlsPadding = new Insets(5, 0, 4, 5);

        this.formGridConfig = new GridBagConstraints();
        this.formGridConfig.fill = GridBagConstraints.NONE;

        this.firstNameValueLabel = new JLabel();
        this.lastNameValueLabel = new JLabel();
        this.cityValueLabel = new JLabel();
        this.customerSinceValueLabel = new JLabel();
        this.editCustomerBtn = new JButton("Edit customer ...");

        addControlWithLabel(firstNameValueLabel, "First Name:", 0);
        addControlWithLabel(lastNameValueLabel, "Last Name:", 1);
        addControlWithLabel(cityValueLabel, "City:", 2);
        addControlWithLabel(customerSinceValueLabel, "Customer Since:", 3);

        formGridConfig.gridy = 4;

        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        formGridConfig.gridx = 1;
        formGridConfig.anchor = GridBagConstraints.LAST_LINE_END;
        formGridConfig.insets = new Insets(0, 0, 0, 0);
        add(editCustomerBtn, formGridConfig);

        editCustomerBtn.addActionListener(e -> this.editItem(e.getSource()));
    }

    @Override
    protected Customer getItem(int id) {
        return this.customerRepository.getCustomer(id);
    }

    @Override
    protected void populateItemInformation(Customer item) {
        firstNameValueLabel.setText(item.getFirstName());
        lastNameValueLabel.setText(item.getLastName());
        cityValueLabel.setText(item.getCity());
        customerSinceValueLabel.setText(item.getCustomerSince().toString());
    }

    private void addControlWithLabel(Component componentToAdd,
                                     String label,
                                     int rowIndex) {
        ControlsHelper.addControlWithLabel(componentToAdd, label, rowIndex, this.controlsPadding, this.formGridConfig, this);
    }
}
