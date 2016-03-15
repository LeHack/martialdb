/*
 ********************************************************************************
  *                        F I L E   S P E C I F I C A T I O N
 ********************************************************************************
 *
 * NAME
 *      webSession.java
 *
 * DESCRIPTION
 *      webSession class
 *
 * MODIFICATION HISTORY
 * Date         Change
 * ------------------------------------------------------------------------------
 * 08-Mar-2016  Initial creation.
 * ------------------------------------------------------------------------------
 */
package pl.samuraj.app.rest.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import org.apache.log4j.Logger;

import pl.samuraj.app.rbac.RoleType;
import pl.samuraj.app.rbac.Roles;

public class webSession {
    // Session time to live = 900 seconds (15 minutes)

    private static final int SESSION_TTL = 900;

    private static final Logger appLog = Logger.getLogger("ipcapSysLog");
    
    public static void create(@Context HttpServletRequest httpRequest, String fullUserName) {
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("fullUserName", fullUserName);
        session.setMaxInactiveInterval(SESSION_TTL);
    }

    public static void invalidate(@Context HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (null != session) {
            session.invalidate();
        }
    }

    public static boolean isValid(@Context HttpServletRequest httpRequest) {
        return null != httpRequest.getSession(false);
    }
    
    public static boolean isValid(@Context HttpServletRequest httpRequest, String fullUserName) {
        boolean result = false;
        if (null != fullUserName) {
            String loggedUser = getFullUserName(httpRequest);
            appLog.debug("loggedUser=" + loggedUser + ";fullUserName=" + fullUserName);
            result = null != loggedUser && fullUserName.equals(loggedUser);
        }
        return result;
    }    

    public static void setRoles(@Context HttpServletRequest httpRequest, Roles roles) {
        HttpSession session = httpRequest.getSession(false);
        if (null != session) {
            session.setAttribute("roles", roles);
        }
    }

    public static Roles getRoles(@Context HttpServletRequest httpRequest) {
        Roles roles = null;
        HttpSession session = httpRequest.getSession(false);
        if (null != session) {
            roles = (Roles) session.getAttribute("roles");
        }
        return roles;
    }

    public static Boolean hasRoles(@Context HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (null != session) {
            Roles roles = (Roles) session.getAttribute("roles");
            return !roles.isEmpty();
        }
        return false;
    }

    public static Boolean hasRole(@Context HttpServletRequest httpRequest, RoleType role) {
        HttpSession session = httpRequest.getSession(false);
        if (null != session) {
            Roles roles = (Roles) session.getAttribute("roles");
            return roles.hasRole(role);
        }
        return false;
    }

    public static String getFullUserName(@Context HttpServletRequest httpRequest) {
        String username = null;
        HttpSession session = httpRequest.getSession(false);
        if (null != session) {
            username = (String) session.getAttribute("fullUserName");
        }
        return username;
    }    
}
