package common;

import java.util.Objects;
import java.util.prefs.Preferences;

public final class PreferencesBasedStore implements SettingsStore {

    private final String SERVER_KEY = "server";
    private final String USER_KEY = "user";
    private final String PASSWORD_KEY = "password";
    private final String PORT_KEY = "port";

    private final Preferences preferences;

    public PreferencesBasedStore() {
        this.preferences = Preferences.userRoot().node("car-showroom-db");
    }

    @Override
    public final ApplicationSettings getSettings() {
        String server = preferences.get(SERVER_KEY, "locahost");
        String user = preferences.get(USER_KEY, "carshowroomuser");
        String password = preferences.get(PASSWORD_KEY, "");
        int port = preferences.getInt(PORT_KEY, 1433);

        return new ApplicationSettings(server, user, password.toCharArray(), port);
    }

    @Override
    public final void saveSettings(ApplicationSettings settings) {
        Objects.requireNonNull(settings);
        preferences.put(SERVER_KEY, settings.getServer());
        preferences.put(USER_KEY, settings.getUser());
        preferences.put(PASSWORD_KEY, new String(settings.getPassword()));
        preferences.putInt(PORT_KEY, settings.getPort());
    }
}
