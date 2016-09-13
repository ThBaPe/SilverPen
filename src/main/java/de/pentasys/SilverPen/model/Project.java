package de.pentasys.SilverPen.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Project
 *
 */
@NamedQueries({ 
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findAllByUser", query = "SELECT p FROM Project p JOIN p.users u WHERE u.email = :userMail")
})
@Entity
@Table (name="Project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String findAll = "Project.findAll";
    public static final String findAllByUser = "Project.findAllByUser";
    
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
	private String projectnumber;
	private String name;
	
	@ManyToMany (fetch=FetchType.EAGER)
	private Collection<User> users = new LinkedList<User>();
	

	public Project() {
		super();
	}   
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getProjectnumber() {
		return this.projectnumber;
	}

	public void setProjectnumber(String projectnumber) {
		this.projectnumber = projectnumber;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
    public Collection<User> getUsers() {
        return users;
    }
    public void setUsers(Collection<User> users) {
        this.users = users;
    }
    
    public String toString(){
        return this.projectnumber+" - "+this.name;
    }
    
    @Override
    public boolean equals(Object p){
        if (! (p instanceof Project)){
            return false;
        } else {
            return (this.projectnumber.equals(((Project)p).projectnumber) && this.name.equals(((Project)p).name));
        }
    }
}