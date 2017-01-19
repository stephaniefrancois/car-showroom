package common;

public final class ApplicationSettings {
    private final String server;
    private final String user;

    public String getServer() {
        return server;
    }

    public String getUser() {
        return user;
    }

    public char[] getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    private final char[] password;
    private final int port;

    public ApplicationSettings(String server,
                               String user,
                               char[] password,
                               int port) {
        this.server = server;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    @Override
    public String toString() {
        return "ApplicationSettings{" +
                "server='" + server + '\'' +
                ", user='" + user + '\'' +
                ", password='?'" +
                ", port=" + port +
                '}';
    }
}
