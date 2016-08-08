package de.pentasys.SilverPen.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * Entity implementation class for Entity: User
 *
 */

@NamedQueries({ 
            @NamedQuery(name = "User.existsUser", query = "SELECT c FROM User c WHERE c.email = :email"),
            @NamedQuery(name = "User.allUsers", query = "SELECT u FROM User u")
})
@Entity
@Table(name = "USER")
public class User implements Serializable {

    public static final String existsUser = "User.existsUser";
    public static final String allUsers = "User.allUsers";

    @Id
    @Column(unique = true, nullable = false, precision = 80)
    private String email;
    @Column(unique = true, nullable = false, precision = 20)
    private String username;
    @Column(nullable = false)
    private String password;
    private static final long serialVersionUID = 1L;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private Collection<Role> roles = new LinkedList<Role>();
    
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private Collection<Project> projects = new LinkedList<Project>();

    public User() {
        super();
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    
    /**
     * Prüft, ob dem Benutzer eine angegebene Rolle zugewiesen wurde
     * 
     * @param rolename Der Rollenname, nachdem gefragt wird
     * @return true, wenn eine Rolle mit dem angegebenen Rollennamen in der roles Collection existiert; false, wenn die Rolle nicht existiert.
     */
    public boolean hasRole(String rolename){
        for (Role role : roles){
            if (role.getRolename().equals(rolename)){
                return true;
            }
        }
        return false;
    }
    
    public boolean equals(User u){
        if(this.getEmail().equals(u.getEmail())){
            return true;
        } else {
            return false;
        }
    }

    public Collection<Project> getProjects() {
        return projects;
    }

    public void setProjects(Collection<Project> projects) {
        this.projects = projects;
    }

}
