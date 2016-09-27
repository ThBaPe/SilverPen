package de.pentasys.SilverPen.model.booking;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: WorkshopBooking
 *
 */
@Entity
@DiscriminatorValue("Workshop")
public class WorkshopBooking extends BookingItem{
  
    public enum StatusWorkshop{
        WORKSHOP_REQUESTED,
        WORKSHOP_CONFIRMED,
        WORKSHOP_REJECTED
    }
    
}