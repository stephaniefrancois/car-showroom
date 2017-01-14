package app.customers.listing;

import app.customers.CustomerEventArgs;

import java.util.EventListener;

public interface CustomerListener extends EventListener {
    void customerDeleted(CustomerEventArgs e);

    void customerSelected(CustomerEventArgs e);

    void customerCreationRequested();
}
