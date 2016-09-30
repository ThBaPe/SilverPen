package de.pentasys.SilverPen.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name="Workshop_User")
public class WorkshopParticipant implements Serializable {

    private static final long serialVersionUID = -7428205098442564564L;

    public enum WorkshopRole{
        PARTICIPANT
    }
    
    public enum ParticipantState{
        REQUESTED,
        CONFIRMED,
        QUEUE_UP,
        RESCINDED
    }
    
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
        @OneToOne       private User users;
                        private String Role;
                        private String State;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public User getUsers() {
        return users;
    }
    
    public void setUsers(User users) {
        this.users = users;
    }
    
    public String getRole() {
        return Role;
    }
    
    public void setRole(String role) {
        Role = role;
    }
    
    public String getState() {
        return State;
    }
    
    public void setState(String state) {
        State = state;
    }
}
