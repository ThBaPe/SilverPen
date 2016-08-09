package de.pentasys.SilverPen.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Project
 *
 */
@Entity
@Table (name="Project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
	private String projectnumber;
	private String name;
	
	@ManyToMany
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
   
}