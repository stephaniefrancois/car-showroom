package app.sales.listing;


import app.common.listing.TableModel;
import core.deal.model.CarDeal;

public final class CarDealTableModel extends TableModel<CarDeal> {
    public CarDealTableModel() {
        super(new String[]{"Deal Date", "Car", "Amount", "Customer", "Duration In Months"});
    }

    @Override
    public Object getValueAt(int row, int col) {
        CarDeal value = getValueAt(row);

        switch (col) {
            case 0:
                return value.getDealDate();
            case 1:
                return value.getCarTitle();
            case 2:
                return value.getDealAmount();
            case 3:
                return value.getCustomerFullName();
            case 4:
                return value.getDurationInMonths();
        }

        return null;
    }
}
