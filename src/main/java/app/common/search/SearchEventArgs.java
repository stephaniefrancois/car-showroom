package app.common.search;

import java.util.EventObject;

public final class SearchEventArgs extends EventObject {
    private final String searchCriteria;

    public SearchEventArgs(Object source, String searchCriteria) {
        super(source);
        this.searchCriteria = searchCriteria;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }
}
