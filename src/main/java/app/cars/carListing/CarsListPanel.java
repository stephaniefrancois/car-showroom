package app.cars.carListing;

import app.cars.CarEventArgs;
import app.objectComposition.ServiceLocator;
import app.styles.BorderStyles;
import app.styles.ComponentSizes;
import common.IRaiseEvents;
import common.ListenersManager;
import core.domain.car.Car;
import core.stock.CarStock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class CarsListPanel extends JPanel implements IRaiseEvents<CarListener> {
    private final CarTableModel tableModel;
    private final JTable carsTable;
    private final CarStock carStock;
    private final JPopupMenu popup;
    private final ListenersManager<CarListener> listeners;

    public CarsListPanel() {
        listeners = new ListenersManager<>();

        // TODO: pass the data from the main controller or app bootstrapper service ???
        this.carStock = ServiceLocator
                .getComposer()
                .getCarStockService();

        setMinimumSize(ComponentSizes.MINIMUM_CAR_LIST_PANEL_SIZE);
        setLayout(new BorderLayout());
        setBorder(BorderStyles.getTitleBorder("Available cars:"));
        this.tableModel = new CarTableModel();
        this.carsTable = new JTable(tableModel);
        this.carsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(this.carsTable), BorderLayout.CENTER);
        popup = this.configurePopupMenu();
        this.refresh();
    }

    public void refresh() {
        // TODO: load this using multi-threading
        this.tableModel.setData(carStock.getAvailableCars());
        this.tableModel.fireTableDataChanged();
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
                listeners.notifyListeners(CarListener::carCreationRequested));

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
            CarEventArgs event = new CarEventArgs(source, car.getCarId());
        listeners.notifyListeners(l -> l.carSelected(event));
    }

    private void askUserToDeleteCar(ActionEvent arg0, int row, Car car) {
        int action = JOptionPane.showConfirmDialog(CarsListPanel.this,
                String.format("Do you really want to delete '%s %s' ?", car.getMake(), car.getModel()),
                "Confirm car deletion", JOptionPane.YES_NO_OPTION);

        // TODO: consider messaging between components, currently we need to inform other components that the CAR has been deleted
        if (action == JOptionPane.YES_OPTION) {
            // TODO: dispatch message about deleted CAR
            System.out.println("Deleting table row at '" + row + "' index ..."); // TODO: add logging statement
            carStock.removeCar(car.getCarId()); //TODO: is this really the right place for this dependency??? Should we bubble this up via events?
            CarTableModel tableModel = (CarTableModel) carsTable.getModel();
            tableModel.removeRow(row);
            notifyListenersAboutDeletedCar(arg0, car);
        }
    }

    private void notifyListenersAboutDeletedCar(ActionEvent arg0, Car car) {
        CarEventArgs event = new CarEventArgs(arg0.getSource(), car.getCarId());
        this.listeners.notifyListeners(l -> l.carDeleted(event));
    }

    @Override
    public void addListener(CarListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(CarListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }
}
