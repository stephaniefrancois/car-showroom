package app.common.listing;

import core.IHaveIdentifier;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TableModel<TModel extends IHaveIdentifier> extends AbstractTableModel {

    private final String[] colNames;
    private List<TModel> data;

    public TableModel(String[] colNames) {
        this.colNames = colNames;
        this.data = new ArrayList<>();
    }

    @Override
    public final int getColumnCount() {
        return colNames.length;
    }

    @Override
    public final String getColumnName(int column) {
        return colNames[column];
    }

    public final void setData(List<TModel> data) {
        this.data = data;
    }

    @Override
    public final int getRowCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public final TModel getValueAt(int row) {
        return data.get(row);
    }

    public final void removeRow(int rowIndex) {
        data.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public final List<ListItem<TModel>> findById(int identifier) {
        return this.data.stream()
                .filter(item -> item.getId() == identifier)
                .map(item -> {
                    int index = this.data.indexOf(item);
                    return new ListItem<>(index, item);
                })
                .collect(Collectors.toList());
    }
}
