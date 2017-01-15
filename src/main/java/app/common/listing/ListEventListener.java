package app.common.listing;

import app.common.BasicEventArgs;

import java.util.EventListener;

public interface ListEventListener extends EventListener {
    void itemDeleted(BasicEventArgs e);

    void itemSelected(BasicEventArgs e);

    void itemCreationRequested();
}
