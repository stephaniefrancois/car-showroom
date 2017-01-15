package app.cars.listing;

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
import core.domain.car.Car;
import core.stock.CarStock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public final class CarsListPanel extends JPanel implements IRaiseEvents<ListEventListener>, SearchListener {
    private static final String CARS_PANEL_KEY = "CarsPanelKey";
    private static final String CARS_NOT_FOUND_PANEL_KEY = "CarsNotFoundPanelKey";
    private final CarTableModel tableModel;
    private final JTable carsTable;
    private final CarStock carStock;
    private final JPopupMenu popup;
    private final ListenersManager<ListEventListener> listeners;
    private final JPanel carsPanel;
    private final JPanel noCarsFoundPanel;
    private final JLabel noCarsFoundLabel;
    private final CardLayout contentPresenter;
    private final JButton addNewCarButton;

    public CarsListPanel() {
        listeners = new ListenersManager<>();

        // TODO: pass the data from the main controller or app bootstrapper service ???
        this.carStock = ServiceLocator
                .getComposer()
                .getCarStockService();

        setMinimumSize(ComponentSizes.MINIMUM_LIST_PANEL_SIZE);
        contentPresenter = new CardLayout();
        setLayout(contentPresenter);

        setBorder(BorderStyles.getTitleBorder("Available cars:"));
        this.tableModel = new CarTableModel();
        this.carsTable = new JTable(tableModel);
        this.carsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.carsPanel = new JPanel();
        this.carsPanel.setLayout(new BorderLayout());
        this.carsPanel.add(new JScrollPane(this.carsTable), BorderLayout.CENTER);

        this.noCarsFoundLabel = new JLabel();
        this.noCarsFoundLabel.setFont(LabelStyles.getFontForHeaderLevelOne());
        this.addNewCarButton = new JButton("Add new car ...");
        this.addNewCarButton.addActionListener(e -> {
            listeners.notifyListeners(ListEventListener::itemCreationRequested);
        });

        this.noCarsFoundPanel = new JPanel();
        this.noCarsFoundPanel.setLayout(new BorderLayout());
        JPanel noCarsContainer = new JPanel();
        noCarsContainer.setLayout(new FlowLayout());
        noCarsContainer.add(noCarsFoundLabel);
        noCarsContainer.add(addNewCarButton);
        this.noCarsFoundPanel.add(noCarsContainer);

        add(this.carsPanel, CARS_PANEL_KEY);
        add(this.noCarsFoundPanel, CARS_NOT_FOUND_PANEL_KEY);

        popup = this.configurePopupMenu();
        this.refresh();
    }

    public void refresh() {
        // TODO: load this using multi-threading
        List<Car> cars = carStock.getAvailableCars();
        if (cars.isEmpty()) {
            this.addNewCarButton.setVisible(true);
            this.displayNoCarsFound("No cars available in the showroom!");
            return;
        }

        this.tableModel.setData(carStock.getAvailableCars());
        this.tableModel.fireTableDataChanged();
        this.displayCars();
    }

    private void displayNoCarsFound(String message) {
        this.noCarsFoundLabel.setText(message);
        this.contentPresenter.show(this, CARS_NOT_FOUND_PANEL_KEY);
    }

    private void displayCars() {
        this.contentPresenter.show(this, CARS_PANEL_KEY);
    }

    private JPopupMenu configurePopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem addCarMenu = new JMenuItem("Add car ...");
        JMenuItem removeCarMenu = new JMenuItem("Delete car ...");
        menu.add(addCarMenu);
        menu.add(removeCarMenu);

        carsTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int row = carsTable.rowAtPoint(e.getPoint());
                selectCarByRowIndex(e.getSource(), row);

                if (e.getButton() == MouseEvent.BUTTON3) {
                    menu.show(carsTable, e.getX(), e.getY());
                }
            }
        });

        carsTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    int row = carsTable.getSelectedRow();
                    selectCarByRowIndex(e.getSource(), row);
                }
            }
        });

        addCarMenu.addActionListener(arg ->
                listeners.notifyListeners(ListEventListener::itemCreationRequested));

        removeCarMenu.addActionListener(arg -> {
            int row = carsTable.getSelectedRow();
            if (row > -1) {
                Car car = tableModel.getValueAt(row);
                askUserToDeleteCar(arg, row, car);
            }
        });

        return menu;
    }

    private void selectCarByRowIndex(Object source, int rowIndex) {

        carsTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
        if (rowIndex > -1) {
            Car car = tableModel.getValueAt(rowIndex);
            notifyListenersAboutSelectedCar(source, car);
        }
    }

    private void notifyListenersAboutSelectedCar(Object source, Car car) {
        BasicEventArgs event = new BasicEventArgs(source, car.getCarId());
        listeners.notifyListeners(l -> l.itemSelected(event));
    }

    private void askUserToDeleteCar(ActionEvent arg0, int row, Car car) {
        int action = JOptionPane.showConfirmDialog(CarsListPanel.this,
                String.format("Do you really want to delete '%s %s' ?", car.getMake(), car.getModel()),
                "Confirm car deletion", JOptionPane.YES_NO_OPTION);

        if (action == JOptionPane.YES_OPTION) {
            System.out.println("Deleting table row at '" + row + "' index ..."); // TODO: add logging statement
            this.refresh();
            this.carStock.removeCar(car.getCarId());
            CarTableModel tableModel = (CarTableModel) carsTable.getModel();
            tableModel.removeRow(row);
            notifyListenersAboutDeletedCar(arg0, car);
            if (tableModel.getRowCount() == 0) {
                this.refresh();
            }
        }
    }

    private void notifyListenersAboutDeletedCar(ActionEvent arg0, Car car) {
        BasicEventArgs event = new BasicEventArgs(arg0.getSource(), car.getCarId());
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
        List<Car> cars = this.carStock.find(e.getSearchCriteria());
        if (cars.isEmpty()) {
            this.addNewCarButton.setVisible(false);
            this.displayNoCarsFound(String.format("No cars found with search criteria '%s' ...", e.getSearchCriteria()));
            return;
        }
        this.tableModel.setData(cars);
        this.tableModel.fireTableDataChanged();
        this.displayCars();
    }

    @Override
    public void resetSearch() {
        this.refresh();
    }
}
