package app.cars.carListing;

import app.cars.CarEventArgs;
import app.objectComposition.ServiceLocator;
import app.styles.BorderStyles;
import app.styles.ComponentSizes;
import common.IRaiseEvents;
import core.domain.car.Car;
import core.stock.CarStock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public final class CarsListPanel extends JPanel implements IRaiseEvents<CarListener> {
    private final CarTableModel tableModel;
    private final JTable carsTable;
    private final CarStock carStock;
    private final JPopupMenu popup;
    private final List<CarListener> listeners;

    public CarsListPanel() {
        listeners = new ArrayList<>();

        // TODO: pass the data from the main controller or app bootstrapper service ???
        this.carStock = ServiceLocator
                .getComposer()
                .getCarStockService();

        setMinimumSize(ComponentSizes.MINIMUM_CAR_LIST_PANEL_SIZE);
        setLayout(new BorderLayout());
        setBorder(BorderStyles.getTitleBorder("Available cars:"));
        tableModel = new CarTableModel();
        carsTable = new JTable(tableModel);
        add(new JScrollPane(carsTable), BorderLayout.CENTER);
        popup = new JPopupMenu();

        configurePopupMenu();

        // TODO: load this using multi-threading
        tableModel.setData(carStock.getAvailableCars());
    }

    private void configurePopupMenu() {
        JMenuItem removeItem = new JMenuItem("Delete car ...");
        popup.add(removeItem);

        carsTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int row = carsTable.rowAtPoint(e.getPoint());
                selectCarByRowIndex(e.getSource(), row);

                if (e.getButton() == MouseEvent.BUTTON3) {
                    popup.show(carsTable, e.getX(), e.getY());
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

        removeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = carsTable.getSelectedRow();
                if (row > -1) {
                    Car car = tableModel.getValueAt(row);
                    askUserToDeleteCar(arg0, row, car);
                }
            }
        });
    }

    private void selectCarByRowIndex(Object source, int rowIndex) {

        carsTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
        if (rowIndex > -1) {
            Car car = tableModel.getValueAt(rowIndex);
            notifyListenersAboutSelectedCar(source, car);
        }
    }

    private void notifyListenersAboutSelectedCar(Object source, Car car) {
        if (listeners.size() > 0) {
            CarEventArgs event = new CarEventArgs(source, car.getCarId());
            listeners.forEach(listener -> listener.carSelected(event));
        }
    }

    private void askUserToDeleteCar(ActionEvent arg0, int row, Car car) {
        int action = JOptionPane.showConfirmDialog(CarsListPanel.this,
                String.format("Do you really want to delete '%s %s' ?", car.getMake(), car.getModel()),
                "Confirm Exit", JOptionPane.YES_NO_OPTION);

        // TODO: consider messaging between components, currently we need to inform other components that the CAR has been deleted
        if (action == JOptionPane.YES_OPTION) {
            // TODO: dispatch message about deleted CAR
            System.out.println("Deleting table row at '" + row + "' index ..."); // TODO: add logging statement
            CarTableModel tableModel = (CarTableModel) carsTable.getModel();
            carStock.removeCar(car.getCarId()); //TODO: is this really the right place for this dependency??? Should we bubble this up via events?
            tableModel.removeRow(row);
            notifyListenersAboutDeletedCar(arg0, car);
        }
    }

    private void notifyListenersAboutDeletedCar(ActionEvent arg0, Car car) {
        if (listeners.size() > 0) {
            CarEventArgs event = new CarEventArgs(arg0.getSource(), car.getCarId());
            listeners.forEach(listener -> listener.carDeleted(event));
        }
    }

    @Override
    public void addListener(CarListener listenerToAdd) {
        if (!listeners.contains(listenerToAdd)) {
            listeners.add(listenerToAdd);
        }
    }

    @Override
    public void removeListener(CarListener listenerToRemove) {
        if (listeners.contains(listenerToRemove)) {
            listeners.remove(listenerToRemove);
        }
    }
}
