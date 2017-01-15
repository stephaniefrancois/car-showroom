package app.customers.details;

import app.common.BasicEventArgs;
import app.common.details.ItemDetailsListener;
import app.objectComposition.ServiceLocator;
import app.styles.LabelStyles;
import common.IRaiseEvents;
import common.ListenersManager;
import core.domain.deal.CustomerProperties;
import data.CustomerRepository;

import javax.swing.*;
import java.awt.*;

public class PreviewSelectedCustomerPanel extends JPanel implements IRaiseEvents<ItemDetailsListener> {

    private final GridBagConstraints formGridConfig;
    private final JLabel firstNameValueLabel;
    private final JLabel lastNameValueLabel;
    private final JLabel cityValueLabel;
    private final JLabel customerSinceValueLabel;

    private final JButton editCustomerBtn;
    private final ListenersManager<ItemDetailsListener> listeners;
    private final CustomerRepository customerRepository;
    private final Insets controlsPadding;
    private CustomerProperties customer;

    public PreviewSelectedCustomerPanel() {
        setLayout(new GridBagLayout());

        this.customer = null;
        this.listeners = new ListenersManager<>();
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
        formGridConfig.anchor = GridBagConstraints.FIRST_LINE_START;
        formGridConfig.insets = new Insets(0, 0, 0, 0);
        add(editCustomerBtn, formGridConfig);

        editCustomerBtn.addActionListener(e -> {
            if (customer == null) {
                return;
            }
            BasicEventArgs event = new BasicEventArgs(e.getSource(), customer.getCustomerId());
            listeners.notifyListeners(l -> l.itemEditRequested(event));
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

    public void previewCustomer(int customerId) {
        CustomerProperties customer = this.customerRepository.getCustomer(customerId);
        if (customer == null) {
            this.customer = null;
            // TODO: log a warning that customer was not found
            // TODO: RAISE event to tell that customer was not found and that NOT FOUND screen would be displayed
            return;
        }

        this.customer = customer;
        populateCustomerInformation(customer);
    }

    private void populateCustomerInformation(CustomerProperties customer) {
        firstNameValueLabel.setText(customer.getFirstName());
        lastNameValueLabel.setText(customer.getLastName());
        cityValueLabel.setText(customer.getCity());
        customerSinceValueLabel.setText(customer.getCustomerSince().toString());
    }

    @Override
    public void addListener(ItemDetailsListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(ItemDetailsListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }
}
