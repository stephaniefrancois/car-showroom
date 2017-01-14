package app.cars.search;

import java.util.EventObject;

public final class CarSearchEventArgs extends EventObject {
    private final String searchCriteria;

    public CarSearchEventArgs(Object source, String searchCriteria) {
        super(source);
        this.searchCriteria = searchCriteria;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }
}
