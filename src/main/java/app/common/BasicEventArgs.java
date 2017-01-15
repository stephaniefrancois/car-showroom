package app.common;

import java.util.EventObject;

public final class BasicEventArgs extends EventObject {
    private final int id;

    public BasicEventArgs(Object source, int id) {
        super(source);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
