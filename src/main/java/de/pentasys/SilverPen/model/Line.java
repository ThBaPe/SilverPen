package de.pentasys.SilverPen.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Line
 *
 */
@Entity

public class Line implements Serializable {


    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
	private String name;
	private User manager;

	@OneToMany(mappedBy="line")
	private List<User> users;
	
	public Line() {
		super();
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public User getManager() {
		return this.manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}
   
}
