package de.pentasys.SilverPen.service;

import java.util.List;

import org.mockito.internal.util.collections.ListUtil;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.service.TimeService.SORT_TYPE;
import de.pentasys.SilverPen.service.TimeService.TIME_BOX;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;



public class CompositeTimeService implements TimeService{
    //Collection of child graphics.
    private LinkedList<TimeService> childTimeService = new LinkedList<TimeService>();

    /**
     * Vergleich zweier BookingItems
     * @param b1 Das Element auf der linken-Seite
     * @param b2 Das Element auf der rechten-Seite
     * @param sortBy Definition wie verlgichen werden soll
     * @return [-1 => b1 < b2] || [ 0 => b1 == b2] || [1 => b1 > b2]
     */
    static public int Compare(BookingItem b1, BookingItem b2, SORT_TYPE sortBy) {
        
        int retVal = 1;
        
        switch (sortBy) {
        case NONE:
                          if(b1.getId() <  b2.getId()) { retVal = -1; }
                     else if(b1.getId() == b2.getId()) { retVal =  0; }
            break;

        case START:
                         if(b1.getStart().getTime() <  b2.getStart().getTime()) { retVal = -1; }
                    else if(b1.getStart().getTime() == b2.getStart().getTime()) { retVal =  0; }
            break;

        case STOP:
                        if(b1.getStop().getTime() <  b2.getStop().getTime()) { retVal = -1; }
                   else if(b1.getStop().getTime() == b2.getStop().getTime()) { retVal =  0; }
            break;

        case SIZE:
                     if((b1.getStop().getTime() - b1.getStart().getTime()) <  (b2.getStop().getTime() - b2.getStart().getTime())) { retVal = -1; }
                else if((b1.getStop().getTime() - b1.getStart().getTime()) == (b2.getStop().getTime() - b2.getStart().getTime())) { retVal = -1; }
            break;
            
        default:
            throw new NotImplementedException();
        }
        
        return retVal;
    }

    //Adds the graphic to the composition.
    public void add(TimeService srv) {
        childTimeService.add(srv);
    }

    //Removes the graphic from the composition.
    public void remove(TimeService srv) {
        childTimeService.remove(srv);
    }
    
    /**
     * Liefert den Namen und die darunter zusammengefasste Service's
     */
    @Override
    public String getServiceName() {
        
        String sName =  this.getClass().getName() + "{ ";
        for (TimeService srv: childTimeService) {
            sName += srv.getServiceName() + ",";
        }
        
        sName += "} ";
        return sName;
    }

    
    /**
     * Verbucht die Stunden
     */
    @Override
    public void commitTime(User user, BookingItem toTime) {
        for (TimeService srv: childTimeService) {
            srv.commitTime(user, toTime);
        }
    }

    /**
     * Verbucht die Stunden
     */
    @Override
    public void commitTime(User user, BookingItem toTime, String id) {
        for (TimeService srv: childTimeService) {
            srv.commitTime(user, toTime, id);
        }
    }

    /**
     * Hilfsfunktion um zwei Listen zusammen zu führen
     * @param left  Erste Liste
     * @param right Zweite Liste
     * @param sort  Das Kriterium nachdem sortiert werden soll
     * @return      Der merge
     */
    public LinkedList<BookingItem> combine(LinkedList<BookingItem> left, LinkedList<BookingItem> right, SORT_TYPE sort) {
        
        LinkedList<BookingItem> merged = new LinkedList<BookingItem>();

        while(!left.isEmpty() || !right.isEmpty()) {
            
            // Wenn eine Liste leer läuft, dann können wir den Rest übertragen
            if(left.isEmpty() && !right.isEmpty()){
                merged.addAll(right);
                break;
            }
            
            if(!left.isEmpty() && right.isEmpty()){
                merged.addAll(left);
                break;
            }
            
            // Falls beide listen noch gefüllt sind müssen wir die Head-Elemente vergleichen
            merged.add( Compare(left.peek(),right.peek(),sort) == -1 ? left.pop() : right.pop());
        }
        
        return merged;
    }
    
    /**
     * Liefert die Liste aller Buchungsel
     */
    @Override
    public List<BookingItem> getBookingList(User user,TIME_BOX box, Date pinDate, SORT_TYPE sort) {
        
        LinkedList<BookingItem> items = new LinkedList<BookingItem>();
       
        for (TimeService srv: childTimeService) {
            LinkedList<BookingItem> newItems = new LinkedList<BookingItem>(srv.getBookingList(user, box, pinDate, sort));
            items = combine(items, newItems, sort);
        }

        return items;
    }
}
