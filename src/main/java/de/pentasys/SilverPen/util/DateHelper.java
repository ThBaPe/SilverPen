package de.pentasys.SilverPen.util;

import java.util.AbstractMap;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import de.pentasys.SilverPen.service.TimeService.TIME_BOX;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DateHelper {
    
    /**
     * Liefert zu einem Datum die Zeitspanne
     * @param box Die Zeitspanne (Tag / Woche / Monat )
     * @param pinDate Das Datum von dem aus die Spanne gebildet werden woll. Wird z.B. die Woche angefragt,
     * dann liegt das pinDate innerhalb der angefragten Woche. 
     * @return Erstes und letztes Datum der Zeitspanne
     */
    public static Map.Entry<Date,Date> GetSpan(TIME_BOX box, Date pinDate) {
        
        GregorianCalendar start = new GregorianCalendar();
        GregorianCalendar stop  = new GregorianCalendar();
        
        // Für die Zeitspanne den Temrin normalisieren
        start.setTime(pinDate);
        start.set(Calendar.HOUR_OF_DAY,0);
        start.set(Calendar.MINUTE,0);
        start.set(Calendar.SECOND,0);
        
        
        switch (box) {
        case DAY:
            
            // Letzter möglicher Zeitpunkt des Tages setzten
            stop.setTime(start.getTime());
            stop.add(Calendar.DATE, 1);
            stop.add(Calendar.SECOND,-1);
            
            break;

        case WEEK:

            // Startdatum an den Anfang der Woche stellen
            int dayOfWeek = start.get(GregorianCalendar.DAY_OF_WEEK);
            int difToMonday = dayOfWeek == GregorianCalendar.SUNDAY ? 6 : dayOfWeek - GregorianCalendar.MONDAY; 
            start.add(Calendar.DATE,-difToMonday);

            // Letzter möglicher Zeitpunkt der Woche setzten
            stop.setTime(start.getTime());
            stop.add(Calendar.DATE, 7);
            stop.add(Calendar.SECOND,-1);
            
            break;

        case MOTH:
            
            // Startdatum an den Anfang des Monats setzten
            start.set(Calendar.DATE,1);

            // Letzter möglicher Zeitpunkt des Monats setzen
            stop.setTime(start.getTime());
            stop.add(Calendar.MONTH, 1);
            stop.add(Calendar.SECOND,-1);

            break;

        default:
            throw new NotImplementedException();
        }
        
        return new AbstractMap.SimpleEntry<Date, Date>(start.getTime(), stop.getTime());
    }
}