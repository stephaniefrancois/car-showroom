package data.sql;

import common.ApplicationSettings;
import common.SettingsStore;
import data.ConnectionStringProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public abstract class MsSqlRepository {
    private final ConnectionStringProvider connectionStringProvider;
    private final SettingsStore settingsProvider;

    protected MsSqlRepository(ConnectionStringProvider connectionStringProvider, SettingsStore settings) {
        Objects.requireNonNull(connectionStringProvider);
        Objects.requireNonNull(settings);
        this.connectionStringProvider = connectionStringProvider;
        this.settingsProvider = settings;
    }

    protected Connection getConnection() throws SQLException {
        String connectionString = this.connectionStringProvider.getConnectionString();
        ApplicationSettings settings = this.settingsProvider.getSettings();
        return DriverManager.getConnection(connectionString,
                settings.getUser(),
                new String(settings.getPassword()));
    }
}
