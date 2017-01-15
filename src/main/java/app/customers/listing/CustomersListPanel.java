package app.customers.listing;

import app.common.BasicEventArgs;
import app.common.listing.ListEventListener;
import app.common.search.SearchEventArgs;
import app.common.search.SearchListener;
import app.objectComposition.ServiceLocator;
import app.styles.BorderStyles;
import app.styles.ComponentSizes;
import app.styles.LabelStyles;
import common.IRaiseEvents;
import common.ListenersManager;
import core.domain.deal.CustomerProperties;
import data.CustomerRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public final class CustomersListPanel extends JPanel implements IRaiseEvents<ListEventListener>, SearchListener {
    private static final String CUSTOMERS_PANEL_KEY = "CustomersPanelKey";
    private static final String CUSTOMERS_NOT_FOUND_PANEL_KEY = "CustomersNotFoundPanelKey";
    private final CustomerTableModel tableModel;
    private final JTable customersTable;
    private final CustomerRepository customerRepository;
    private final JPopupMenu popup;
    private final ListenersManager<ListEventListener> listeners;
    private final JPanel customersPanel;
    private final JPanel noCustomersFoundPanel;
    private final JLabel noCustomersFoundLabel;
    private final CardLayout contentPresenter;
    private final JButton addNewCustomerButton;

    public CustomersListPanel() {
        listeners = new ListenersManager<>();

        // TODO: pass the data from the main controller or app bootstrapper service ???
        this.customerRepository = ServiceLocator
                .getComposer()
                .getCustomerRepository();

        setMinimumSize(ComponentSizes.MINIMUM_LIST_PANEL_SIZE);
        contentPresenter = new CardLayout();
        setLayout(contentPresenter);

        setBorder(BorderStyles.getTitleBorder("Customers:"));
        this.tableModel = new CustomerTableModel();
        this.customersTable = new JTable(tableModel);
        this.customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.customersPanel = new JPanel();
        this.customersPanel.setLayout(new BorderLayout());
        this.customersPanel.add(new JScrollPane(this.customersTable), BorderLayout.CENTER);

        this.noCustomersFoundLabel = new JLabel();
        this.noCustomersFoundLabel.setFont(LabelStyles.getFontForHeaderLevelOne());
        this.addNewCustomerButton = new JButton("Add new customer ...");
        this.addNewCustomerButton.addActionListener(e -> {
            listeners.notifyListeners(ListEventListener::itemCreationRequested);
        });

        this.noCustomersFoundPanel = new JPanel();
        this.noCustomersFoundPanel.setLayout(new BorderLayout());
        JPanel noCustomersContainer = new JPanel();
        noCustomersContainer.setLayout(new FlowLayout());
        noCustomersContainer.add(noCustomersFoundLabel);
        noCustomersContainer.add(addNewCustomerButton);
        this.noCustomersFoundPanel.add(noCustomersContainer);

        add(this.customersPanel, CUSTOMERS_PANEL_KEY);
        add(this.noCustomersFoundPanel, CUSTOMERS_NOT_FOUND_PANEL_KEY);

        popup = this.configurePopupMenu();
        this.refresh();
    }

    public void refresh() {
        // TODO: load this using multi-threading
        List<CustomerProperties> customers = customerRepository.getCustomers();
        if (customers.isEmpty()) {
            this.addNewCustomerButton.setVisible(true);
            this.displayNoCustomersFound("You have no customers :(");
            return;
        }

        this.tableModel.setData(customerRepository.getCustomers());
        this.tableModel.fireTableDataChanged();
        this.displayCustomers();
    }

    private void displayNoCustomersFound(String message) {
        this.noCustomersFoundLabel.setText(message);
        this.contentPresenter.show(this, CUSTOMERS_NOT_FOUND_PANEL_KEY);
    }

    private void displayCustomers() {
        this.contentPresenter.show(this, CUSTOMERS_PANEL_KEY);
    }

    private JPopupMenu configurePopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem addMenu = new JMenuItem("Add customer ...");
        JMenuItem removeMenu = new JMenuItem("Delete customer ...");
        menu.add(addMenu);
        menu.add(removeMenu);

        customersTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int row = customersTable.rowAtPoint(e.getPoint());
                selectCarByRowIndex(e.getSource(), row);

                if (e.getButton() == MouseEvent.BUTTON3) {
                    menu.show(customersTable, e.getX(), e.getY());
                }
            }
        });

        customersTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    int row = customersTable.getSelectedRow();
                    selectCarByRowIndex(e.getSource(), row);
                }
            }
        });

        addMenu.addActionListener(arg ->
                listeners.notifyListeners(ListEventListener::itemCreationRequested));

        removeMenu.addActionListener(arg -> {
            int row = customersTable.getSelectedRow();
            if (row > -1) {
                CustomerProperties customer = tableModel.getValueAt(row);
                askUserToDeleteCar(arg, row, customer);
            }
        });

        return menu;
    }

    private void selectCarByRowIndex(Object source, int rowIndex) {

        customersTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
        if (rowIndex > -1) {
            CustomerProperties customer = tableModel.getValueAt(rowIndex);
            notifyListenersAboutSelection(source, customer);
        }
    }

    private void notifyListenersAboutSelection(Object source, CustomerProperties customer) {
        BasicEventArgs event = new BasicEventArgs(source, customer.getCustomerId());
        listeners.notifyListeners(l -> l.itemSelected(event));
    }

    private void askUserToDeleteCar(ActionEvent arg0, int row, CustomerProperties customer) {
        int action = JOptionPane.showConfirmDialog(CustomersListPanel.this,
                String.format("Do you really want to delete '%s %s' ?", customer.getFirstName(), customer.getLastName()),
                "Confirm customer deletion", JOptionPane.YES_NO_OPTION);

        if (action == JOptionPane.YES_OPTION) {
            System.out.println("Deleting table row at '" + row + "' index ..."); // TODO: add logging statement
            this.refresh();
            this.customerRepository.removeCustomer(customer.getCustomerId());
            CustomerTableModel tableModel = (CustomerTableModel) customersTable.getModel();
            tableModel.removeRow(row);
            notifyListenersAboutDeletedCustomer(arg0, customer);
            if (tableModel.getRowCount() == 0) {
                this.refresh();
            }
        }
    }

    private void notifyListenersAboutDeletedCustomer(ActionEvent arg0, CustomerProperties customer) {
        BasicEventArgs event = new BasicEventArgs(arg0.getSource(), customer.getCustomerId());
        this.listeners.notifyListeners(l -> l.itemDeleted(event));
    }

    @Override
    public void addListener(ListEventListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(ListEventListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }

    @Override
    public void search(SearchEventArgs e) {
        // TODO: log about executed search
        System.out.println("Searching for: " + e.getSearchCriteria());
        List<CustomerProperties> customers = this.customerRepository.findCustomers(e.getSearchCriteria());
        if (customers.isEmpty()) {
            this.addNewCustomerButton.setVisible(false);
            this.displayNoCustomersFound(String.format("No customers found with search criteria '%s' ...", e.getSearchCriteria()));
            return;
        }
        this.tableModel.setData(customers);
        this.tableModel.fireTableDataChanged();
        this.displayCustomers();
    }

    @Override
    public void resetSearch() {
        this.refresh();
    }
}
