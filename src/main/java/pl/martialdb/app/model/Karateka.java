/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 *  NAME
 *      Karateka.java
 *
 *  DESCRIPTION
 *      Class describing MartialDB Karatekas
 *
 *  MODIFICATION HISTORY
 *  ----------------------------------------------------------------------------
 *  28-May-2016  Initial
 *  ----------------------------------------------------------------------------
 */
package pl.martialdb.app.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.martialdb.app.db.MartialDatabase;

public class Karateka extends KaratekaMetaData {
    final MartialDatabase db;
    static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("appLog");

    private Integer id, groupId;
    private String name, surname, email, telephone, address, city;
    private Rank rank;
    private Date signupDate, birthdate;
    private boolean status;

    public Karateka(MartialDatabase...db){
        this.db = (db.length > 0 ? db[0] : new MartialDatabase());
    }

    public Karateka(int id, MartialDatabase...db) throws NoSuchKaratekaException {
        this(db);
        logger.debug("Creating Karateka instance for id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + sqlFieldsStr + " from karateka where id = ?", id
        );
        try {
            if (row.isClosed())
                throw new NoSuchKaratekaException("No Karateka found for id: " + id);
            row.next();
        } catch (SQLException e) {
            logger.error("SQL Error when constructing karateka object", e);
        }
        init( row );
    }

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public void init(ResultSet data) {
        try {
            this.id          = data.getInt("id");
            this.groupId     = data.getInt("group_id"); // TODO: replace with object?
            this.name        = data.getString("name");
            this.surname     = data.getString("surname");
            this.telephone   = data.getString("telephone");
            this.email       = data.getString("email");
            this.address     = data.getString("address");
            this.city        = data.getString("city");
            this.signupDate  = dateFormat.parse( data.getString("signup") );
            this.birthdate   = dateFormat.parse( data.getString("birthdate") );
            // data.getBoolean() doesn't work correctly with SQLite
            this.status      = "true".equals( data.getString("status") );
            this.rank        = new Rank( data.getString("rank_type"), data.getInt("rank_level") );
        } catch (SQLException | ParseException e) {
            logger.error("Error when initializing karateka", e);
        }
    }

    public int getId() {
        return this.id;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public String getName() {
        return this.name;
    }

    public String getFullName() {
        return this.name + " " + this.surname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public Rank getRank() {
        return this.rank;
    }

    public boolean getStatus() {
        return this.status;
    }

    public Date getSignupDate() {
        return this.signupDate;
    }

    public Date getBirthdate() {
        return this.birthdate;
    }

    public class NoSuchKaratekaException extends Exception {
        private static final long serialVersionUID = 5078582624142838847L;

        public NoSuchKaratekaException(String message) {
            super(message);
        }
    }
}
