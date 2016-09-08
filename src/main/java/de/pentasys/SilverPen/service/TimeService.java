package de.pentasys.SilverPen.service;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface TimeService {
    
    /**
     * Liefert die eindeutige Servicekennung
     * @return [KlassenName] + [ObjektID] 
     */
    String getServiceName();
    
    /**
     * Stundenbuchung ohne Abhängigkeit zu anderen Objekten
     * @param user Der Benutzer der die Stunden verrechnet bekommt
     * @param toTime Die Stunden die auf das Konto des Benutzers gebucht werden sollen
     */
    void commitTime(User user, BookingItem toTime);
    
    /**
     * Stundenbuchung mit Abhängigkeit zu anderen Objekten (Workshop - Projekt - etc.)
     * @param user Der Benutzer der die Stunden verrechnet bekommt
     * @param toTime Die Stunden die auf das Konto des Benutzers gebucht werden sollen
     * @param id 
     */
    void commitTime(User user, BookingItem toTime,String id);

    
    public enum TIME_BOX {
        DAY,
        WEEK,
        MOTH;
        }
    
    public enum SORT_TYPE {
        NONE,
        START,
        STOP,
        SIZE,
    }

    /**
     */
    
    /**
     * Liefert die Stunden zu einer Benutzer
     * @param user Der Benutzer zu den die Stunden gesucht werden sollen
     * @param box  Die Spanne in der die Stunden aufgelistet werden sollen
     * @param pinDate Ein Datum das als gültig angesehen wird, um die Time_Box zu identifizieren
     * @param sort Die gewünschte Sortierung der Buchungen
     * @return  Liste der angeforderten Stundenbuchungen
     */
    List<BookingItem> getBookingList(User user,TIME_BOX box, Date pinDate, SORT_TYPE sort);
}

