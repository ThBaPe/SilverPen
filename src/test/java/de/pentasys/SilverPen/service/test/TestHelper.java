package de.pentasys.SilverPen.service.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.model.booking.VacationBooking;
import de.pentasys.SilverPen.model.booking.WorkshopBooking;

/**
 * Hilfsklasse für die Erstellung und Verarbeitung von TestDaten
 * @author bankieth
 *
 */
public class TestHelper {
    public int INITIAL_DAY;
    public int INITIAL_WEEK;

    public TestHelper(int iNITIAL_DAY, int iNITIAL_WEEK) {
        INITIAL_DAY = iNITIAL_DAY;
        INITIAL_WEEK = iNITIAL_WEEK;
    }
    
    public static SimpleDateFormat dtF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static String convertToString(ProjectBooking boocking) {
        return boocking.getProject().getId() + "|" +  dtF.format(boocking.getStart()) + "|" + dtF.format(boocking.getStop());
    }

    public static String convertToString(VacationBooking boocking) {
        return boocking.getStatus() + "|" +  dtF.format(boocking.getStart()) + "|" + dtF.format(boocking.getStop());
    }
    
    public static String convertToString(BookingItem boocking) {
        return "InternalTime|" +  dtF.format(boocking.getStart()) + "|" + dtF.format(boocking.getStop());
    }
    
    public static String convertToString(WorkshopBooking boocking) {
        return "WorkShop|" +  dtF.format(boocking.getStart()) + "|" + dtF.format(boocking.getStop());
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> filterListItem(List<T> list, Predicate<T> filter) {
        
        List<T> retList = new LinkedList<>();
        List<Object> lo = list.stream().filter(filter).collect(Collectors.toList());
        
        for (Object o : lo) {
            retList.add((T)o);
        }
           
        return retList;
    }

    public static <T extends BookingItem> T convertToObj(String boocking,  Class<T> type) throws ParseException {

        String[] data = boocking.split("\\|");

        BookingItem retVal = null;
        if( type == ProjectBooking.class){
            ProjectBooking proVal = new ProjectBooking();
            proVal.setProject(new Project());
            proVal.getProject().setId(Integer.parseInt(data[0]));
            retVal = proVal;
        } else if(type == VacationBooking.class) {
            VacationBooking vacVal = new VacationBooking();
            vacVal.setStatus(data[0]);
            retVal = vacVal;
        } else if(type == WorkshopBooking.class) {
            retVal = new WorkshopBooking();
        } else if(type == BookingItem.class) {
            retVal = new ProjectBooking();
        }
        
        retVal.setStart(dtF.parse(data[1]));
        retVal.setStop(dtF.parse(data[2]));
        
        return type.cast(retVal);
    }

}