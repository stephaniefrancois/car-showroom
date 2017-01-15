package app.common.details;

import app.common.BasicEventArgs;

import java.util.EventListener;

public interface ItemDetailsListener extends EventListener {
    void itemEditRequested(BasicEventArgs e);

    void itemSaved(BasicEventArgs e);

    void itemEditCancelled(BasicEventArgs e);
}
