/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 *  NAME
 *      User.java
 *
 *  DESCRIPTION
 *      Class describing MartialDB users
 *
 *  NOTES
 *      Too generate a password hash, use: BCrypt.hashpw(password, BCrypt.gensalt());
 *  
 *  MODIFICATION HISTORY
 *  ----------------------------------------------------------------------------
 *  28-May-2016 Initial
 *  01-Jun-2016 Fixed bug in updateLoginStamp()
 *  ----------------------------------------------------------------------------
 */
package pl.martialdb.app.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.rbac.RoleType;

public class User extends BaseModel {
    public User(MartialDatabase...db){
        super(db);
        this.meta = new UserMetaData();
    }

    public User(int id, MartialDatabase...db) throws ObjectNotFoundException {
        this(db);
        this.newObject = false;
        logger.debug("Creating User instance for user id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + meta.getSQLfieldsStr() + " from " + meta.getTblName() + " where id = ?", id
        );
        try {
            if (row.isClosed())
                throw new ObjectNotFoundException("No User found for id: " + id);
            row.next();
        } catch (SQLException e) {
            logger.error("Error when constructing user object", e);
        }
        init( row );
    }

    private final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void init(ResultSet data) {
        try {
            this
                .set("id",            data.getInt("id"))
                .set("login",         data.getString("login"))
                .set("pass",          data.getString("pass"))
                .set("name",          data.getString("name"))
                .set("surname",       data.getString("surname"))
                .set("email",         data.getString("email"))
                .set("defaultCityId", data.getInt("default_city_id"))
                .set("role",          RoleType.valueOf( data.getString("role") ))
                .set("stamp",         dateTimeFormat.parse( data.getString("stamp") ));
        } catch (SQLException | ParseException e) {
            logger.error("Error when initializing user", e);
        }
    }

    public User set(String param, Object value) {
        return (User)super.set(param, value);
    }
    public User save() {
        return (User)super.save();
    }
    protected Object getMappedVal(String param) {
        Object out = null;
        switch (param) {
            case "pass":
                out = BCrypt.hashpw((String)get("pass"), BCrypt.gensalt());
                break;
            default:
                out = super.getMappedVal(param);
                break;
        };
        return out;
    }

    public int getId() {
        return (int)get("id");
    }

    public String getName() {
        return (String)get("name") + " " + (String)get("surname");
    }

    public String getEmail() {
        return (String)get("email");
    }

    public int getDefaultCity() {
        return (int)get("defaultCityId");
    }

    public RoleType getRole() {
        return (RoleType)get("role");
    }

    public String getLogin() {
        return (String)get("login");
    }

    public Boolean comparePassword(StringBuffer password) {
        return BCrypt.checkpw(password.toString(), (String)get("pass"));
    }

    public Date getLastLogin() {
        return (Date)get("stamp");
    }

    public void updateLoginStamp() {
        String now = dateTimeFormat.format(new Date());
        this.db.runUpdate("UPDATE user set stamp = ? where id = ?", Arrays.asList(now, getId()));
    }
}
