package app.customers.listing;


import app.common.listing.TableModel;
import core.domain.deal.CustomerProperties;

public final class CustomerTableModel extends TableModel<CustomerProperties> {
    public CustomerTableModel() {
        super(new String[]{"First Name", "Last Name", "City", "Customer Since"});
    }

    @Override
    public Object getValueAt(int row, int col) {
        CustomerProperties value = getValueAt(row);

        switch (col) {
            case 0:
                return value.getFirstName();
            case 1:
                return value.getLastName();
            case 2:
                return value.getCity();
            case 3:
                return value.getCustomerSince();
        }

        return null;
    }
}
