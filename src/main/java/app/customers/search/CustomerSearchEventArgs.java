package app.customers.search;

import java.util.EventObject;

public final class CustomerSearchEventArgs extends EventObject {
    private final String searchCriteria;

    public CustomerSearchEventArgs(Object source, String searchCriteria) {
        super(source);
        this.searchCriteria = searchCriteria;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }
}
