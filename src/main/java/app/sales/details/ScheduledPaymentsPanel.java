package app.sales.details;

import app.styles.BorderStyles;
import core.deal.model.PaymentSchedule;

import javax.swing.*;
import java.awt.*;

public final class ScheduledPaymentsPanel extends JPanel {

    private final PaymentTableModel paymentsTableModel;
    private final JTable paymentsTable;

    public ScheduledPaymentsPanel() {
        setBorder(BorderStyles.getTitleBorder("Scheduled Payments:"));
        setLayout(new BorderLayout());
        this.paymentsTableModel = new PaymentTableModel();
        this.paymentsTable = new JTable(paymentsTableModel);
        this.paymentsTable.setEnabled(false);
        this.paymentsTable.setBackground(new JLabel().getBackground());
        add(this.paymentsTable, BorderLayout.CENTER);
    }

    public void setScheduledPayments(PaymentSchedule paymentSchedule) {
        this.paymentsTableModel.setData(paymentSchedule.getScheduledPayments());
        this.paymentsTableModel.fireTableDataChanged();
    }
}
