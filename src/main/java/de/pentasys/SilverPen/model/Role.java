package de.pentasys.SilverPen.model;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Rollen für die Rechtevergabe
 */
@NamedQueries({ 
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
    @NamedQuery(name = "Role.findByUser", query = "SELECT r FROM Role r Where r.users = :user") 
    
})
@Entity
@Table(name = "ROLE")
public class Role {

    public static final String findAll = "Role.findAll";
    public static final String findByUser = "Role.findByUser";
    
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String rolename;

    @ManyToMany
    private Collection<User> users = new LinkedList<User>();

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
