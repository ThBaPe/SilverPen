package de.pentasys.SilverPen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Einzelne Stundenbuchung
 */
@Entity
@Table(name="HOUR")
public class Hour {

    @Id @GeneratedValue 
    private int id;
    
    @Column(nullable=false)
    private java.util.Date start;

    @Column(nullable=false)
    private java.util.Date stop;
    
    @Column
    private String description;

    @ManyToOne
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.util.Date getStart() {
        return start;
    }

    public void setStart(java.util.Date start) {
        this.start = start;
    }

    public java.util.Date getStop() {
        return stop;
    }

    public void setStop(java.util.Date stop) {
        this.stop = stop;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
