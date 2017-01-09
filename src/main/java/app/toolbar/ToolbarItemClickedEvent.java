package app.toolbar;

import java.util.EventObject;

public final class ToolbarItemClickedEvent extends EventObject {
    private final String menuItemKey;

    public ToolbarItemClickedEvent(Object source, String menuItemKey) {
        super(source);
        this.menuItemKey = menuItemKey;
    }

    public String getMenuItemKey() {
        return menuItemKey;
    }
}
