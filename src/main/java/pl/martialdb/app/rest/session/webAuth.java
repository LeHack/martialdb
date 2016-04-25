/*
 ********************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 ********************************************************************************
 *
 * NAME
 *      webAuth.java
 *
 * DESCRIPTION
 *      webAuth class
 *
 * MODIFICATION HISTORY
 * -------------------------------------------------------------------------------
 * 08-Mar-2016  Initial creation.
 * -------------------------------------------------------------------------------
 */
package pl.martialdb.app.rest.session;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Vector;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.ietf.jgss.GSSCredential;

import pl.martialdb.app.rbac.Roles;

public class webAuth implements Serializable {

    private static final long serialVersionUID = 8438707594716655427L;
    
    private static final Logger appLog = Logger.getLogger("appLog");

    private Vector<String> groups = new Vector<String>();
    private Roles roles = new Roles();

    private final Collection<String> usersDatabase = new ArrayList<>();    

    /**
     * Creates realm object.
     *
     * @throws NamingException
     * @throws AAAException
     */
    public webAuth() throws NamingException {
        usersDatabase.add("user");
        usersDatabase.add("admin");
    }

    /**
     * Check whether a user can use the application.
     *
     * The first step is authentication based on Active Directory.
     *
     * The second step is to determine whether a user is a member of group that
     * has access to an application
     *
     * @param username
     * @param password
     *
     * @return
     */
    public boolean isValidUser(String fullUsername, StringBuffer password) {
        String[] userDomain = Objects.requireNonNull(fullUsername,
                "User name cannot be null").split("@");

        boolean isValid = false;
        if ( usersDatabase.contains(fullUsername) ){
            groups.add(fullUsername+'s');
            roles.setRoles(roles.groupsToRoles(groups));

            if (roles.isEmpty()) {
                appLog.warn("Authorization of " + fullUsername + " finished with failure.");
            } else {
                appLog.info("Authentication of " + userDomain + " finished with success.");
                isValid = true;
            }
        }
        return isValid;
    }

    public String getUserName(HttpServletRequest httpRequest) {
        return getFullUserName(httpRequest);
    }

    public Roles getRoles() {
        return roles;
    }

    /**
     * Get user name from Http Request or via AAA API if it fails
     *
     * @param httpRequest Http Request to get the user name from
     * @return user@domain.name
     */
    public String getFullUserName(HttpServletRequest httpRequest) {
        String userName = null;
        //Cut some corners: get user name directly from httpRequest
        //may not work for unauthenticated user
        Principal principal = httpRequest.getUserPrincipal();
        if (principal != null) {
            userName = principal.getName();
        }
        return userName;
    }
}
