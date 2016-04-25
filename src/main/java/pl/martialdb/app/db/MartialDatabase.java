/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 *  NAME
 *      ResultSetSerializer.java
 *
 *  DESCRIPTION
 *      ResultSetSerializer class
 *  
 *  NOTE
 *      Download sqlite-jdbc-[ver].jar and put it into Tomcat ${catalina.home} lib,
 *      also create ${catalina.home}/dbs directory.
 *
 *  MODIFICATION HISTORY
 *  ----------------------------------------------------------------------------
 *  25-Apr-2014  Initial
 *  ----------------------------------------------------------------------------
 */
package pl.martialdb.app.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import pl.martialdb.app.rest.RestData;

public class MartialDatabase {
    private DataSource _dataSource;
    
    private static final org.apache.log4j.Logger appLog = org.apache.log4j.Logger.getLogger("appLog");
    
    public MartialDatabase(){
        try {
            Context initialContext = new InitialContext();
            Context environmentContext = (Context) initialContext.lookup("java:comp/env");
            String dataResourceName = "jdbc/MartialDB";
            _dataSource = (DataSource) environmentContext.lookup(dataResourceName);            
        } catch (NamingException e) {
            appLog.error(e.getMessage());
        }
    }
    
    public String getJsonString(String query){
        StringWriter stringWriter = new StringWriter();

        SimpleModule module = new SimpleModule();
        module.addSerializer(new ResultSetSerializer());

        SimpleModule module2 = new SimpleModule();
        module2.addSerializer(new ResultSetMetaDataSerializer());
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        objectMapper.registerModule(module2);

        
        try {
            Connection conn = _dataSource.getConnection();
            try {
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(query);

                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("fields", rs.getMetaData());
                objectNode.putPOJO("records", rs);
                objectMapper.writeValue(stringWriter, objectNode);
                
            } catch (SQLException e) {
                appLog.error(e.getMessage());
            } catch (IOException e) {
                appLog.error(e.getMessage());
            } finally {
                // Release connection back to the pool
                if (conn != null) { conn.close(); }
                conn = null; // prevent any future access
            }
        } catch (SQLException e) {
                appLog.error(e.getMessage());
        }
        return stringWriter.toString();
    }
    
    public String getExampleDataJsonString(){
        return getJsonString("SELECT * FROM t;");
    }
   
}
