package de.pentasys.SilverPen.model.booking;

import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.pentasys.SilverPen.model.User;

/**
 * Einzelne Stundenbuchung
 */
@NamedQueries({ 
    @NamedQuery(name = "BookingItem.findAll", query = "SELECT b FROM BookingItem b"),
    @NamedQuery(name = "BookingItem.findByUser", query = "SELECT b FROM BookingItem b Where b.user = :user"),
    @NamedQuery(name = "BookingItem.findDayByUser", query = "SELECT b FROM BookingItem b Where b.user = :user AND :pinDate BETWEEN b.start AND b.stop"),
    @NamedQuery(name = "BookingItem.findSpanByUser", query = "SELECT b FROM BookingItem b Where b.user = :user AND ((b.start < :spanStart  AND b.stop > :spanStop) OR ((b.start BETWEEN :spanStart AND :spanStop) OR (b.stop BETWEEN :spanStart AND :spanStop)))")
    
})
@Entity
@Table(name="BOOKINGITEM")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="BOOKING_TYPE", discriminatorType=DiscriminatorType.STRING)
public abstract class BookingItem {

	public static final String findAll = "BookingItem.findAll";
    public static final String findByUser = "BookingItem.findByUser";
    public static final String findDayByUser = "BookingItem.findDayByUser";
    public static final String findSpanByUser = "BookingItem.findSpanByUser";
    
    private double sumHours;
    
    @Id 
    @GeneratedValue 
    private int id;
    
    @Column(nullable=false)
    private java.util.Date start;

    @Column(nullable=false)
    private java.util.Date stop;
    
    @Column
    private String description;

    @Column
    private String status;   
    
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
     
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public double getSumHours() {
        return sumHours;
    }

    public void setSumHours(double sumHours) {
        this.sumHours = sumHours;
    }
     
    public double calculateTime() {
        double milliseconds = this.stop.getTime()-this.start.getTime();
        int minutes = (int) (milliseconds / (60 * 1000) % 60);
        int hours = (int) milliseconds / (60 * 60 * 1000) % 24;
        return hours + (double) minutes/60.0;
    }
    
    public String getWeekDay() {      
        String sim = new SimpleDateFormat("EEEEE").format(this.getStart());
        return sim;
    }

    
  
}
