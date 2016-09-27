package de.pentasys.SilverPen.model.booking;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Vacation
 *
 */
@NamedQueries({ 
    @NamedQuery(name = "VacationBooking.findAllByUser", query = "SELECT b FROM BookingItem b Where b.user = :user "),
    @NamedQuery(name = "VacationBooking.findSpanByUser", query = "SELECT b FROM BookingItem b Where b.user = :user AND TYPE(b) = VacationBooking AND ((b.start < :spanStart  AND b.stop > :spanStop) OR ((b.start BETWEEN :spanStart AND :spanStop) OR (b.stop BETWEEN :spanStart AND :spanStop))) ORDER BY b.start"),
    @NamedQuery(name = "VacationBooking.findSpanByUserOrderStop", query = "SELECT b FROM BookingItem b Where b.user = :user AND TYPE(b) = VacationBooking AND ((b.start < :spanStart  AND b.stop > :spanStop) OR ((b.start BETWEEN :spanStart AND :spanStop) OR (b.stop BETWEEN :spanStart AND :spanStop))) ORDER BY b.stop")
})
@Entity
@DiscriminatorValue("Vacation")
public class VacationBooking extends BookingItem{
    public static final String findAllByUser = "VacationBooking.findAllByUser";
    public static final String findSpanByUser = "VacationBooking.findSpanByUser";
    public static final String findSpanByUserOrderStop = "VacationBooking.findSpanByUserOrderStop";
    
    public enum StatusVacation{
        VACATION_REQUESTED,
        VACATION_CONFIRMED,
        VACATION_REJECTED
    }
}