package pl.martialdb.app.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import pl.martialdb.app.db.MartialDatabase;

public class UserCollection extends User {
    private final Collection<User> users;
    
    public UserCollection(MartialDatabase...db) {
        super(db);
        this.users = getAll();
    }

    private Collection<User> getAll() {
        Collection<User> users = new ArrayList<>();
        ResultSet rows = this.db.runQuery(
            "SELECT " + sqlFieldsStr + " from user"
        );
        try {
            while (rows.next()) {
                User user = new User(this.db);
                user.init(rows);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Error when creating user collection", e);
        }
        return users;
    }

    public User findByPassword(String login, StringBuffer password) {
        Iterator<User> iter = this.users.iterator();

        User result = null;
        while (iter.hasNext()) {
            User u = iter.next();
            if (login.equals( u.getLogin() ) && u.comparePassword( password )) {
                result = u;
                break;
            }
        }

        return result;
    }
}
