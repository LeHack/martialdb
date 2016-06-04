package pl.martialdb.app.model;

import java.sql.ResultSet;
import java.util.Iterator;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseFilter;
import pl.martialdb.app.db.MartialDatabase;

public class UserCollection extends BaseCollection {

    // Standard constructor
    public UserCollection(MartialDatabase...db) {
        super(
            (db.length > 0 ? db[0] : new MartialDatabase()),
            new UserMetaData()
        );
    }

    // Single object collection
    public UserCollection(User u) {
        super(u);
    }

    @Override
    protected Object initObject(ResultSet row) {
        User u = new User(db);
        u.init(row);
        return u;
    }

    @Override
    protected BaseFilter getDefaultFilter() {
        return new BaseFilter();
    }

    @Override
    protected boolean filter(BaseFilter f, Object obj) {
        User u = (User) obj;
        boolean result = true;
        if (!f.check( "role", u.getRole())) {
            result = false;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public User findByPassword(String login, StringBuffer password) {
        Iterator<User> iter = (Iterator<User>) getIterator();

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
