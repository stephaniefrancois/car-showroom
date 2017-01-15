package app.customers.details;

import app.common.BasicEventArgs;
import app.common.details.ItemDetailsListener;
import app.common.validation.ValidationSummaryPanel;
import app.objectComposition.ServiceLocator;
import common.IRaiseEvents;
import common.ListenersManager;
import core.customer.CustomerFactory;
import core.customer.CustomerFactoryProvider;
import core.domain.deal.CustomerProperties;
import core.domain.validation.ValidationSummary;
import data.CustomerRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class CustomerEditorPanel extends JPanel implements
        IRaiseEvents<ItemDetailsListener> {

    private final JButton saveButton;
    private final JButton cancelButton;

    private final ListenersManager<ItemDetailsListener> listeners;
    private final CustomerRepository customerRepository;
    private final CustomerFactoryProvider customerFactoryProvider;
    private final CustomerEditorInputsPanel inputsPanel;
    private final ValidationSummaryPanel validationMessagesPanel;
    private CustomerFactory customerFactory;

    public CustomerEditorPanel() {
        setLayout(new BorderLayout());

        this.listeners = new ListenersManager<>();
        this.customerRepository = ServiceLocator.getComposer().getCustomerRepository();
        this.customerFactoryProvider = ServiceLocator.getComposer().getCustomerFactoryProvider();
        this.customerFactory = this.customerFactoryProvider.createCustomerFactory();

        inputsPanel = new CustomerEditorInputsPanel();
        validationMessagesPanel = new ValidationSummaryPanel();

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        controlsPanel.add(saveButton);
        controlsPanel.add(cancelButton);

        add(inputsPanel, BorderLayout.NORTH);
        add(validationMessagesPanel, BorderLayout.CENTER);
        add(controlsPanel, BorderLayout.SOUTH);

        this.configureButtonHandlers();
    }

    private void configureButtonHandlers() {
        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelCustomerEditing(e);
            }
        });

        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCustomer(e);
            }
        });
    }

    private void cancelCustomerEditing(ActionEvent e) {
        BasicEventArgs args = new BasicEventArgs(e.getSource(), customerFactory.getCustomerId());
        listeners.notifyListeners(l -> l.itemEditCancelled(args));
    }

    private void saveCustomer(ActionEvent e) {
        this.customerFactory = this.inputsPanel.mapFormValuesToCustomerFactory(this.customerFactory);

        ValidationSummary validationSummary = customerFactory.validate();
        this.validationMessagesPanel
                .displayValidationResults(validationSummary,
                        this.inputsPanel.getFieldsMap());

        if (validationSummary.getIsValid() == false) {
            System.err.println(String.format("Customer with ID: '%d' is NOT valid!", customerFactory.getCustomerId())); // TODO: log this
            return;
        }

        System.out.println(String.format("Customer with ID: '%d' is valid ...", customerFactory.getCustomerId()));

        try {
            CustomerProperties customer = customerFactory.build();
                customer = customerRepository.saveCustomer(customer);
            BasicEventArgs args = new BasicEventArgs(e.getSource(), customerFactory.getCustomerId());
            listeners.notifyListeners(l -> l.itemSaved(args));
        } catch (Exception ex) {
            ex.printStackTrace(); // TODO: log error regarding customer validation
        }
    }

    public void createCustomer() {
        this.customerFactory = this.customerFactoryProvider.createCustomerFactory();
        this.inputsPanel.mapCustomerValuesToForm(this.customerFactory);
    }

    public void editCustomer(int customerId) {
        System.out.println("Editing customer with id: " + customerId); // TODO: add logging statement about customer being edited
        CustomerProperties customer = this.customerRepository.getCustomer(customerId);
        this.customerFactory = this.customerFactoryProvider.createCustomerFactory(customer);
        this.inputsPanel.mapCustomerValuesToForm(this.customerFactory);
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
