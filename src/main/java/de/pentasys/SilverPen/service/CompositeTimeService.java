package de.pentasys.SilverPen.service;

import java.util.List;

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
            sName = srv.getServiceName() + ",";
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
     * Hilfsfunktion für die Sortierung von Buchungselementen die über ein einheitliches Interface geladen werden
     * @param baseList Die Liste zu den die neuen Items übernommen werden müssen
     * @param newItems Die neuen Listenelemente
     * @param sort Das Kriterium nachdem sortiert werden soll
     */
    public LinkedList<BookingItem> combine(LinkedList<BookingItem> baseList, LinkedList<BookingItem> newItems, SORT_TYPE sort) {
        
        if(baseList.isEmpty() && newItems.isEmpty()) {
            return new LinkedList<BookingItem>();
        }

        if(baseList.isEmpty() || newItems.isEmpty()) {
            return baseList.isEmpty() ? newItems : baseList;
        }

        // Die kleinere Liste auf die Linke Seite, da wir damit iterationen sparen können
        LinkedList<BookingItem> left  = baseList.size() < newItems.size() ? baseList : newItems;
        LinkedList<BookingItem> right = baseList.size() < newItems.size() ? newItems : baseList;
        // left.indexOf()
        
        Iterator<BookingItem> itrL = left.iterator();
        int indexL = 0;
        
        while(right.size() > 0) {
            // Sollange wir noch Elemente hinzufügen müssen
            
            if(!itrL.hasNext()) {
                // Linke Liste ist ans Ende gelaufen, jetzt kanna alles auf einen Schlag übernommen werden
                left.addAll(right);
            } else {

                // das nächste Element der Linken-Liste
                BookingItem nextL = itrL.next();
                
                while(right.size() > 0 && Compare(right.peek(),nextL,sort) == -1) {
                    // Das neue Element muss vorher in die Liste einsortiert werden,
                    // der Index Zeit immer auf die vorherige Listenposition, daher Post-Increment
                    left.set(indexL++,right.pop());
                }
            } // if(itrL.hasNext())
        }// while(right.size() > 0) 
        
        return left;
    }
    
    /**
     * Liefert die Liste aller Buchungsel
     */
    @Override
    public List<BookingItem> getBookingList(User user,TIME_BOX box, Date pinDate, SORT_TYPE sort) {
        
        LinkedList<BookingItem> items = new LinkedList<BookingItem>();

        for (TimeService srv: childTimeService) {
            LinkedList<BookingItem> newItems = new LinkedList<BookingItem>(srv.getBookingList(user, box, pinDate, sort));
            combine(items, newItems, sort);
        }

        return items;
    }
}
