package app.customers.search;

import java.util.EventListener;

public interface CustomerSearchListener extends EventListener {
    void searchForCustomers(CustomerSearchEventArgs e);
    void resetSearch();
}
