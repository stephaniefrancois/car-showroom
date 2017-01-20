package common;

import data.ConnectionStringProvider;

import java.util.Objects;

public class ApplicationSettingsBasedConnectionStringProvider implements ConnectionStringProvider {
    private final SettingsStore settingsStore;
    private final String DATABASE_NAME = "CarShowoom";

    public ApplicationSettingsBasedConnectionStringProvider(SettingsStore settingsStore) {
        Objects.requireNonNull(settingsStore);
        this.settingsStore = settingsStore;
    }

    @Override
    public final String getConnectionString() {
        ApplicationSettings settings = this.settingsStore.getSettings();
        return String.format("jdbc:sqlserver://%s:%d;databasename=%s", settings.getServer(),
                settings.getPort(), DATABASE_NAME);
    }
}
