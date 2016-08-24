package de.pentasys.SilverPen.model.booking;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Vacation
 *
 */
@Entity
@Table(name = "VACATIONBOOKING")
@DiscriminatorValue("Vacation")
public class VacationBooking extends BookingItem{
 

    
}