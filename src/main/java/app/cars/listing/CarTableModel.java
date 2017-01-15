package app.cars.listing;

import app.common.listing.TableModel;
import core.stock.model.Car;

public final class CarTableModel extends TableModel<Car> {
    public CarTableModel() {
        super(new String[] { "Make", "Model", "Condition", "Price" });
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
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
}
