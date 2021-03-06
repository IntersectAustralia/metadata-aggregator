// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.domain;

import au.org.intersect.sydma.webapp.domain.PermissionEntry;
import au.org.intersect.sydma.webapp.domain.ResearchGroup;
import au.org.intersect.sydma.webapp.domain.Role;
import au.org.intersect.sydma.webapp.domain.UserType;
import java.lang.Boolean;
import java.lang.String;
import java.util.Set;

privileged aspect User_Roo_JavaBean {
    
    public String User.getUsername() {
        return this.username;
    }
    
    public void User.setUsername(String username) {
        this.username = username;
    }
    
    public String User.getPassword() {
        return this.password;
    }
    
    public void User.setPassword(String password) {
        this.password = password;
    }
    
    public String User.getSurname() {
        return this.surname;
    }
    
    public void User.setSurname(String surname) {
        this.surname = surname;
    }
    
    public String User.getGivenname() {
        return this.givenname;
    }
    
    public void User.setGivenname(String givenname) {
        this.givenname = givenname;
    }
    
    public String User.getEmail() {
        return this.email;
    }
    
    public void User.setEmail(String email) {
        this.email = email;
    }
    
    public String User.getInstitution() {
        return this.institution;
    }
    
    public void User.setInstitution(String institution) {
        this.institution = institution;
    }
    
    public Boolean User.getEnabled() {
        return this.enabled;
    }
    
    public void User.setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public Set<Role> User.getRoles() {
        return this.roles;
    }
    
    public void User.setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public UserType User.getUserType() {
        return this.userType;
    }
    
    public void User.setUserType(UserType userType) {
        this.userType = userType;
    }
    
    public Set<PermissionEntry> User.getPermissionEntries() {
        return this.permissionEntries;
    }
    
    public void User.setPermissionEntries(Set<PermissionEntry> permissionEntries) {
        this.permissionEntries = permissionEntries;
    }
    
    public Set<ResearchGroup> User.getResearchGroups() {
        return this.researchGroups;
    }
    
    public void User.setResearchGroups(Set<ResearchGroup> researchGroups) {
        this.researchGroups = researchGroups;
    }

   public void User.setHasRstudioAccount(Boolean hasRstudioAccount) {
       this.hasRstudioAccount = hasRstudioAccount;
   }
    
}
