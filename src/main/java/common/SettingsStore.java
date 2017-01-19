package common;

public interface SettingsStore {
    ApplicationSettings getSettings();

    void saveSettings(ApplicationSettings settings);
}
