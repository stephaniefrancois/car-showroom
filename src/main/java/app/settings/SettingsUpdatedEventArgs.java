package app.settings;

import common.ApplicationSettings;

import java.util.EventObject;

public final class SettingsUpdatedEventArgs extends EventObject {

    private final ApplicationSettings settings;

    public SettingsUpdatedEventArgs(Object source,
                                    ApplicationSettings settings) {
        super(source);
        this.settings = settings;
    }

    public ApplicationSettings getSettings() {
        return settings;
    }
}
