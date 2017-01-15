package app.common.search;

import java.util.EventListener;

public interface SearchListener extends EventListener {
    void search(SearchEventArgs e);
    void resetSearch();
}
