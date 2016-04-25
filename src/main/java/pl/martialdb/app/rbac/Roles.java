/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 *  NAME
 *      Roles.java
 *
 *  DESCRIPTION
 *      Roles class
 *
 *  MODIFICATION HISTORY
 *  -----------------------------------------------------------------------------
 *  08-Mar-2016  Initial
 *  -----------------------------------------------------------------------------
 */

package pl.martialdb.app.rbac;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

public class Roles {

    private Set<RoleType> roles;

    public Roles(){;}

    public void Roles(Set<RoleType> Roles){
        this.setRoles( Roles );
    }
    
    public void Roles(Collection<String> groups){
        this.setRoles( groupsToRoles(groups) );
    }

    public void setRoles(Set<RoleType> Roles){
        this.roles = Roles;
    }

    public Set<RoleType> getRoles(){
        return this.roles;
    }
    
    public Set<RoleType> groupToRoles( String group ){
        switch (group) {
            case "users": return EnumSet.of(RoleType.USERS);
            case "admins": return EnumSet.of(RoleType.USERS, RoleType.ADMINS);
            default: return EnumSet.noneOf(RoleType.class);
        }
    }

    public Set<RoleType> groupsToRoles( Collection<String> groups ){
        Set<RoleType> retRoles = EnumSet.noneOf(RoleType.class);
        if ( groups != null ){
            for ( String group: groups) {
                retRoles.addAll( groupToRoles(group) );
            }
        }
        return retRoles;
    }

    public boolean isEmpty(){
        return roles.isEmpty();
    }
    
    public boolean hasRole(RoleType Role){
        return roles.contains(Role);
    }

}
