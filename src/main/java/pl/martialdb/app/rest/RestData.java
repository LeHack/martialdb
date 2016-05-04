/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.martialdb.app.rest;

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
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.db.ResultSetSerializer;
import pl.martialdb.app.rbac.RoleType;
import pl.martialdb.app.rest.session.webSession;


@Path("/data")
public class RestData {
    
    @Context
    private HttpServletRequest httpRequest;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Response exampleData() {
        if ( webSession.hasRoles(httpRequest) ){
            MartialDatabase db = new MartialDatabase();
            return Response.status(Response.Status.OK).entity( db.getExampleDataJsonString() ).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }    
}
