package app.sales.details;

import app.common.listing.TableModel;
import core.deal.model.ScheduledPayment;

public final class PaymentTableModel extends TableModel<ScheduledPayment> {

    public PaymentTableModel() {
        super(new String[]{"Payment Date", "Amount"});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ScheduledPayment value = getValueAt(rowIndex);

        switch (columnIndex) {
            case 0:
                return value.getPaymentDate();
            case 1:
                return value.getAmount();
        }

        return null;
    }
}
