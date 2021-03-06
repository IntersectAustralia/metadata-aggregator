// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.domain;

import au.org.intersect.sydma.webapp.domain.User;
import au.org.intersect.sydma.webapp.domain.UserType;
import java.lang.Long;
import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect User_Roo_Finder {
    
    public static TypedQuery<User> User.findUsersByEmailEquals(String email) {
        if (email == null || email.length() == 0) throw new IllegalArgumentException("The email argument is required");
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE o.email = :email", User.class);
        q.setParameter("email", email);
        return q;
    }
    
    public static TypedQuery<User> User.findUsersByEmailEqualsAndUserType(String email, UserType userType) {
        if (email == null || email.length() == 0) throw new IllegalArgumentException("The email argument is required");
        if (userType == null) throw new IllegalArgumentException("The userType argument is required");
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE o.email = :email  AND o.userType = :userType", User.class);
        q.setParameter("email", email);
        q.setParameter("userType", userType);
        return q;
    }
    
    public static TypedQuery<User> User.findUsersByGivennameEquals(String givenname) {
        if (givenname == null || givenname.length() == 0) throw new IllegalArgumentException("The givenname argument is required");
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE o.givenname = :givenname", User.class);
        q.setParameter("givenname", givenname);
        return q;
    }
    
    public static TypedQuery<User> User.findUsersByUserType(UserType userType) {
        if (userType == null) throw new IllegalArgumentException("The userType argument is required");
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE o.userType = :userType", User.class);
        q.setParameter("userType", userType);
        return q;
    }
    
    public static TypedQuery<User> User.findUsersByUserTypeAndIdEquals(UserType userType, Long id) {
        if (userType == null) throw new IllegalArgumentException("The userType argument is required");
        if (id == null) throw new IllegalArgumentException("The id argument is required");
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE o.userType = :userType AND o.id = :id", User.class);
        q.setParameter("userType", userType);
        q.setParameter("id", id);
        return q;
    }
    
    public static TypedQuery<User> User.findUsersByUserTypeAndUsernameEquals(UserType userType, String username) {
        if (userType == null) throw new IllegalArgumentException("The userType argument is required");
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE o.userType = :userType AND o.username = :username", User.class);
        q.setParameter("userType", userType);
        q.setParameter("username", username);
        return q;
    }
    
    public static TypedQuery<User> User.findUsersByUsernameEquals(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE o.username = :username", User.class);
        q.setParameter("username", username);
        return q;
    }
    
    public static TypedQuery<User> User.findUsersByUsernameLikeOrGivennameLikeOrSurnameLike(String username, String givenname, String surname) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        username = username.replace('*', '%');
        if (username.charAt(0) != '%') {
            username = "%" + username;
        }
        if (username.charAt(username.length() - 1) != '%') {
            username = username + "%";
        }
        if (givenname == null || givenname.length() == 0) throw new IllegalArgumentException("The givenname argument is required");
        givenname = givenname.replace('*', '%');
        if (givenname.charAt(0) != '%') {
            givenname = "%" + givenname;
        }
        if (givenname.charAt(givenname.length() - 1) != '%') {
            givenname = givenname + "%";
        }
        if (surname == null || surname.length() == 0) throw new IllegalArgumentException("The surname argument is required");
        surname = surname.replace('*', '%');
        if (surname.charAt(0) != '%') {
            surname = "%" + surname;
        }
        if (surname.charAt(surname.length() - 1) != '%') {
            surname = surname + "%";
        }
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE LOWER(o.username) LIKE LOWER(:username)  OR LOWER(o.givenname) LIKE LOWER(:givenname)  OR LOWER(o.surname) LIKE LOWER(:surname)", User.class);
        q.setParameter("username", username);
        q.setParameter("givenname", givenname);
        q.setParameter("surname", surname);
        return q;
    }
    
}
