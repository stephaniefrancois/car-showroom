package app.cars.carListing;

import core.domain.car.Car;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public final class CarTableModel extends AbstractTableModel {

    private List<Car> data;

    private String[] colNames = {"Make", "Model", "Condition", "Price"};

    public CarTableModel() {
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setData(List<Car> data) {
        this.data = data;
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public int getRowCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public Car getValueAt(int row) {
        return data.get(row);
    }

    @Override
    public Object getValueAt(int row, int col) {
        Car car = getValueAt(row);

        switch (col) {
            case 0:
                return car.getMake();
            case 1:
                return car.getModel();
            case 2:
                return car.getCondition();
            case 3:
                return car.getPrice();
        }

        return null;
    }

    public void removeRow(int rowIndex) {
        data.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
