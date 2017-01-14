package app.customers.listing;


import core.domain.deal.CustomerProperties;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public final class CustomerTableModel extends AbstractTableModel {

    private List<CustomerProperties> data;

    private String[] colNames = {"First Name", "Last Name", "City", "Customer Since"};

    public CustomerTableModel() {
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setData(List<CustomerProperties> data) {
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

    public CustomerProperties getValueAt(int row) {
        return data.get(row);
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

    public void removeRow(int rowIndex) {
        data.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
