package app.customers.details;


import app.customers.CustomerEventArgs;

import java.util.EventListener;

public interface CustomerDetailsListener extends EventListener {
    void customerEditRequested(CustomerEventArgs e);

    void customerSaved(CustomerEventArgs e);

    void customerEditCancelled(CustomerEventArgs e);
}
