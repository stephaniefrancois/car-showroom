package data.sql;

import app.RootLogger;
import common.SettingsStore;
import core.authentication.model.User;
import core.authentication.model.UserProfile;
import data.ConnectionStringProvider;
import data.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MsSqlUserRepository extends MsSqlRepository implements UserRepository {
    private static final Logger log = RootLogger.get();

    private final String GET_USER_USING_CREDENTIALS = "SELECT l.LoginId, l.PasswordHash, " +
            "p.UserProfileId, p.FirstName, p.LastName " +
            "FROM Logins l " +
            "INNER JOIN UserProfiles p ON l.UserProfileId = p.UserProfileId " +
            "WHERE l.LoginName = ?";

    public MsSqlUserRepository(ConnectionStringProvider connectionStringProvider, SettingsStore settings) {
        super(connectionStringProvider, settings);
    }

    @Override
    public List<User> findUsersByCredentials(String userName, String passwordHash) {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_USER_USING_CREDENTIALS)
        ) {
            statement.setString(1, userName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int userProfileId = rs.getInt("UserProfileId");
                String hash = rs.getString("PasswordHash");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");

                if (hash.equals(passwordHash)) {
                    return Collections.singletonList(new User(userProfileId, userName,
                            new UserProfile(firstName, lastName)));
                }
            } else {
                log.warning(() -> String.format("USER with NAME '%s' was not found!", userName));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e, () -> String.format("Failed to retrieve '%s' USER!", userName));
        }

        return Collections.EMPTY_LIST;
    }
}
