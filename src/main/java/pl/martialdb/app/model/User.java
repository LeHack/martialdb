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

import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.exceptions.ObjectNotFoundException;

public class User extends UserMetaData {
    final MartialDatabase db;

    private Integer id, defaultCity;
    private String login, pass, name, surname, email, role;
    private Date stamp;

    public User(MartialDatabase...db){
        this.db = (db.length > 0 ? db[0] : new MartialDatabase());
    }

    public User(int id, MartialDatabase...db) throws ObjectNotFoundException {
        this(db);
        logger.debug("Creating User instance for user id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + sqlFieldsStr + " from " + tblName + " where id = ?", id
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

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void init(ResultSet data) {
        try {
            this.id          = data.getInt("id");
            this.login       = data.getString("login");
            this.pass        = data.getString("pass");
            this.name        = data.getString("name");
            this.surname     = data.getString("surname");
            this.email       = data.getString("email");
            this.role        = data.getString("role"); // TODO, change to ENUM
            this.defaultCity = data.getInt("default_city_id");
            this.stamp       = dateFormat.parse( data.getString("stamp") );
        } catch (SQLException | ParseException e) {
            logger.error("Error when initializing user", e);
        }
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name + " " + this.surname;
    }

    public String getEmail() {
        return this.email;
    }

    public int getDefaultCity() {
        return this.defaultCity;
    }

    public String getRole() {
        return this.role;
    }

    public String getLogin() {
        return this.login;
    }

    public Boolean comparePassword(StringBuffer password) {
        return BCrypt.checkpw(password.toString(), this.pass);
    }

    public Date getLastLogin() {
        return this.stamp;
    }

    public void updateLoginStamp() {
        String now = dateFormat.format(new Date());
        this.db.runUpdate("UPDATE user set stamp = ? where id = ?", Arrays.asList(now, this.id));
    }
}
