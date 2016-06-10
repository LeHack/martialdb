/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 *  NAME
 *      MartialDatabase.java
 *
 *  DESCRIPTION
 *      Main db class
 *
 *  NOTE
 *      Download sqlite-jdbc-[ver].jar and put it into Tomcat ${catalina.home} lib,
 *      also create ${catalina.home}/dbs directory.
 *
 *  MODIFICATION HISTORY
 *  ----------------------------------------------------------------------------
 *  25-Apr-2016 Initial
 *  29-Jun-2016 Rewrote most of the module. Connections are left open and reused.
 *              Added simpler methods to execute queries.
 *  01-Jun-2016 Updated query execution methods to allow for inserts/updates.
 *  ----------------------------------------------------------------------------
 */
package pl.martialdb.app.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MartialDatabase {
    private final Connection dbConnection;
    private static final org.apache.log4j.Logger appLog = org.apache.log4j.Logger.getLogger("appLog");

    public MartialDatabase(Connection...conn){
        this.dbConnection = (conn.length > 0 ? conn[0] : getConnection());
    }
    private Connection getConnection() {
        Connection conn = null;
        try {
            Context initialContext = new InitialContext();
            Context environmentContext = (Context) initialContext.lookup("java:comp/env");
            String dataResourceName = "jdbc/MartialDB";
            DataSource ds = (DataSource) environmentContext.lookup(dataResourceName);
            conn = ds.getConnection();
        } catch (NamingException | SQLException e) {
            appLog.error("Error preparing DB connection", e);
        }
        return conn;
    }

    public ResultSet runSimpleQuery(String query){
        ResultSet rs = null;
        try {
            Statement stm = dbConnection.createStatement();
            rs = stm.executeQuery(query);
        } catch (SQLException e) {
            appLog.error("Error executing query", e);
        }
        return rs;
    }

    public ResultSet runQuery(String query){
        return runQuery(query, Arrays.asList());
    }
    public ResultSet runQuery(String query, Object single){
        return runQuery(query, Arrays.asList(single));
    }
    public ResultSet runQuery(String query, List<Object> params){
        CheckedFunction<PreparedStatement, Object> executor = stm -> stm.executeQuery();
        return (ResultSet)_runQuery(query, params, executor);
    }

    public ResultSet runUpdate(String query){
        return (ResultSet)runUpdate(query, Arrays.asList());
    }
    public ResultSet runUpdate(String query, Object single){
        return (ResultSet)runUpdate(query, Arrays.asList(single));
    }
    public ResultSet runUpdate(String query, List<Object> params){
        CheckedFunction<PreparedStatement, Object> executor = stm -> { stm.executeUpdate(); return stm.getGeneratedKeys(); };
        return (ResultSet)_runQuery(query, params, executor);
    }

    @FunctionalInterface
    public interface CheckedFunction<T, R> {
       R apply(T t) throws SQLException;
    }

    private Object _runQuery(String query, List<Object> params, CheckedFunction<PreparedStatement, Object> exec){
        Object rs = null;
        try {
            PreparedStatement stm = dbConnection.prepareStatement(query);
            int i = 1;
            for (Object p : params) {
                stm.setObject(i++, p);
            }
            rs = exec.apply(stm);
        } catch (SQLException e) {
            appLog.error("Error executing query: " + query, e);
        }
        return rs;
    }
}
