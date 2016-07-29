package de.pentasys.SilverPen.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Rollen für die Rechtevergabe
 */
@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String rolename;

    @ManyToMany
    private Collection<User> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolenname) {
        this.rolename = rolenname;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    
}
