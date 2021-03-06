package de.pentasys.SilverPen.model;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import de.pentasys.SilverPen.model.booking.BookingItem;
@NamedQueries({ 
    @NamedQuery(name = "Workshop.findAll", query = "SELECT w FROM Workshop w"),
    @NamedQuery(name = "Workshop.findByUserAndRole", query = "SELECT w FROM Workshop w JOIN w.participant p WHERE p.Role in :roles AND p.users in :users")
})
@Entity
@Table(name = "Workshop")
public class Workshop {
    
    public static final String findAll = "Workshop.findAll";
    public static final String findByUserAndRole = "Workshop.findByUserAndRole";

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private int id;
    @Column(nullable = false) @Future                     private Date start;
    @Column(nullable = false) @Future                     private Date stop;
    @Column(nullable = false)                             private String title;
    @Column(nullable = false)                             private String description;
    @Column(nullable = false)                             private String tutor;
    @Column(nullable = false)                             private String organizer;
    @Column(nullable = false)                             private String location;
    @Column(nullable = false)                             private String status;
    @Column(nullable = false) @Max(100) @Min(1)           private int maxParticipants;
    @OneToMany(fetch = FetchType.EAGER)                   private Collection<BookingItem> hours;
    @OneToMany(fetch = FetchType.EAGER)                   private Collection<WorkshopParticipant> participant = new LinkedList<WorkshopParticipant>();

    public Collection<WorkshopParticipant> getParticipant() {
        return participant;
    }

    public void setParticipant(Collection<WorkshopParticipant> participant) {
        this.participant = participant;
    }

    public Collection<BookingItem> getHours() {
        return hours;
    }

    public void setHours(Collection<BookingItem> hours) {
        this.hours = hours;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

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
}
