package app.customers;

import java.util.EventObject;

public final class CustomerEventArgs extends EventObject {
    private final int customerId;

    public CustomerEventArgs(Object source, int customerId) {
        super(source);
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }
}
