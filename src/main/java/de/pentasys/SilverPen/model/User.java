package de.pentasys.SilverPen.model;

import java.io.Serializable;
import java.lang.String;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */

@NamedQueries({ @NamedQuery(name = "User.existsUser", query = "SELECT c FROM User c WHERE c.email = :email") })
@Entity
@Table(name = "USER")
public class User implements Serializable {

    public static final String existsUser = "User.existsUser";

    @Id
    @Column(unique = true, nullable = false, precision = 80)
    private String email;
    @Column(unique = true, nullable = false, precision = 20)
    private String username;
    @Column(nullable = false)
    private String password;
    private static final long serialVersionUID = 1L;

    @ManyToMany(mappedBy = "users")
    private Collection<Role> roles = new LinkedList<Role>();

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

}
