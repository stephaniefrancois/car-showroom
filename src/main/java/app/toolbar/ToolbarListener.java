package app.toolbar;

import java.util.EventListener;

public interface ToolbarListener extends EventListener {
    void toolbarItemClicked(ToolbarItemClickedEvent e);
}
