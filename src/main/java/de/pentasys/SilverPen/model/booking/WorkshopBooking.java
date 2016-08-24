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
@Table(name = "WORKSHOPBOOKING")
@DiscriminatorValue("Workshop")
public class WorkshopBooking extends BookingItem{
  
    
}