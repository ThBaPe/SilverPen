package de.pentasys.SilverPen.model.booking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: ProjectBooking
 *
 */
@Entity
@Table(name = "PROJECTBOOKING")
public class ProjectBooking extends BookingItem{
 
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(nullable = false)
    private String status;   
    
}