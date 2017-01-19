package app.settings;

import java.util.EventListener;

public interface SettingsEventListener extends EventListener {
    void settingsModified(SettingsUpdatedEventArgs args);
}
