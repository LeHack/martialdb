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
 *  28-May-2014  Initial
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

public class User {
    final MartialDatabase db;
    static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("appLog");

    private Integer id;
    private String login, pass, name, surname, email, role, defaultCity;
    private Date stamp;

    public User(MartialDatabase...db){
        this.db = (db.length > 0 ? db[0] : new MartialDatabase());
    }

    public User(int id, MartialDatabase...db) {
        this(db);
        logger.debug("Creating User instance for user id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT id, login, pass, name, surname, email, role, defaultCity, stamp from user where id = ?", id
        );
        try {
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
            this.defaultCity = data.getString("defaultCity"); // TODO: Change to foreign key
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

    public String getDefaultCity() {
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
        this.db.runQuery("UPDATE user set stamp = ? where id = ?", Arrays.asList(now, this.id));
    }
}
