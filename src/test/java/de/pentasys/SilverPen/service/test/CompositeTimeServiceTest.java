package de.pentasys.SilverPen.service.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.print.Book;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.AssertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.Workshop;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.model.booking.VacationBooking;
import de.pentasys.SilverPen.model.booking.WorkshopBooking;
import de.pentasys.SilverPen.service.CompositeTimeService;
import de.pentasys.SilverPen.service.TimeService;
import de.pentasys.SilverPen.service.TimeService.SORT_TYPE;
import de.pentasys.SilverPen.service.TimeService.TIME_BOX;


@RunWith(MockitoJUnitRunner.class)
public class CompositeTimeServiceTest {
    
    private static User user1; //< ts1, ts2, ts3, ts4, 
    private static User user2; //< ---, ts2, ts3, ---, 
    private static User user3; //< ---, ---, ts3, ---, 
    private static User user4; //< ts1, ---, ---, ---,  
    private static User user5; //< ---, ts2, ---, ts4,
    private static User user6; //< ---, ---, ---, ---, 
    
    private static TimeService ts1; //< user1, -----, -----, user4, ----- //< Projektstunden
    private static TimeService ts2; //< user1, user2  -----, -----, user5 //< Urlaub
    private static TimeService ts3; //< user1, user2, user3  -----, ----- //< Workshop
    private static TimeService ts4; //< user1, user2, user3  -----, user5 //< Intern
    
    //             Month < WEEK < DAY > > >
    private static List<List<List<BookingItem>>> Month = new ArrayList<List<List<BookingItem>>>();

    
    private static SimpleDateFormat dtF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private static String convertToString(ProjectBooking boocking) {
        return boocking.getProject().getId() + "|" +  dtF.format(boocking.getStart()) + "|" + dtF.format(boocking.getStop());
    }

    private static String convertToString(VacationBooking boocking) {
        return boocking.getStatus() + "|" +  dtF.format(boocking.getStart()) + "|" + dtF.format(boocking.getStop());
    }
    
    private static String convertToString(BookingItem boocking) {
        return "InternalTime|" +  dtF.format(boocking.getStart()) + "|" + dtF.format(boocking.getStop());
    }
    
    private static String convertToString(WorkshopBooking boocking) {
        return "WorkShop|" +  dtF.format(boocking.getStart()) + "|" + dtF.format(boocking.getStop());
    }


    private static <T extends BookingItem> T convertToObj(String boocking,  Class<T> type) throws ParseException {

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
        } else if(type == WorkshopBooking.class
                    || type == BookingItem.class) {
            // noch nichts zu tun
            retVal = new WorkshopBooking();
        }
        
        retVal.setStart(dtF.parse(data[1]));
        retVal.setStop(dtF.parse(data[2]));
        return type.cast(retVal);

    }

    
    
    
    
    @Before
    public void setUp() throws Exception {
        
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user4 = new User();
        user5 = new User();

        
        // Statischen Felder initialisieren
        ts1 = mock(TimeService.class);
        ts2 = mock(TimeService.class);
        ts3 = mock(TimeService.class);
        ts4 = mock(TimeService.class);

        
        when(ts1.getServiceName()).thenReturn("MockTimeServiceProject");
        when(ts2.getServiceName()).thenReturn("MockTimeServiceUrlaub");
        when(ts3.getServiceName()).thenReturn("MockTimeServiceWorkShop");
        when(ts4.getServiceName()).thenReturn("MockTimeServiceIntern");

 
        // Aufbau Projekte:         "ProjektID|        Start      |        Stop       "    
        //  -"-   Urlaub:      "StatusVacation|        Start      |        Stop       "    
        //  -"-   Workshop:    "DummyText     |        Start      |        Stop       "    

        LinkedList<BookingItem> Monday = new LinkedList<BookingItem>(Arrays.asList(
                convertToObj("1|2007-12-31 07:00:00|2007-12-31 09:00:00",ProjectBooking.class),
                convertToObj("1|2007-12-31 09:00:00|2007-12-31 11:00:00",ProjectBooking.class),
                convertToObj("2|2007-12-31 11:00:00|2007-12-31 13:00:00",ProjectBooking.class),
                convertToObj("1|2007-12-31 14:00:00|2007-12-31 16:00:00",ProjectBooking.class),
                convertToObj("2|2007-12-31 16:00:00|2007-12-31 18:00:00",ProjectBooking.class),
                convertToObj("1|2007-12-31 18:00:00|2007-12-31 19:00:00",ProjectBooking.class)));

        LinkedList<BookingItem> Tuesday = new LinkedList<BookingItem>(Arrays.asList(
                convertToObj("VACATION_CONFIRMED|2008-01-01 08:00:00|2008-01-01 16:00:00",VacationBooking.class)
                ));

        LinkedList<BookingItem> Wednesday = new LinkedList<BookingItem>(Arrays.asList(
                convertToObj(                 "1|2008-01-02 07:00:00|2008-01-02 08:00:00",ProjectBooking.class),
                convertToObj("VACATION_CONFIRMED|2008-01-02 08:00:00|2008-01-02 16:00:00",VacationBooking.class)
                ));

        LinkedList<BookingItem> Thursday = new LinkedList<BookingItem>(Arrays.asList(
                convertToObj("Workshop|2008-01-03 07:00:00|2008-01-03 12:00:00",WorkshopBooking.class),
                convertToObj(       "2|2008-01-03 12:00:00|2008-01-03 14:00:00",ProjectBooking.class),
                convertToObj("Workshop|2008-01-03 14:00:00|2008-01-03 19:00:00",WorkshopBooking.class)
                ));

        LinkedList<BookingItem> Friday = new LinkedList<BookingItem>(Arrays.asList(
                convertToObj(       "2|2008-01-04 07:00:00|2008-01-04 10:00:00",ProjectBooking.class),
                convertToObj(       "3|2008-01-04 10:00:00|2008-01-04 13:00:00",ProjectBooking.class),
                convertToObj("Internal|2008-01-04 13:00:00|2008-01-04 14:00:00",BookingItem.class),
                convertToObj(       "2|2008-01-04 14:00:00|2008-01-04 18:00:00",ProjectBooking.class),
                convertToObj(       "1|2008-01-04 18:00:00|2008-01-04 19:00:00",ProjectBooking.class)
                ));

        LinkedList<BookingItem> Saturday = new LinkedList<BookingItem>(Arrays.asList(
                convertToObj("Worksho2|2008-01-05 08:00:00|2008-01-05 10:00:00",WorkshopBooking.class),
                convertToObj("Internal|2008-01-05 10:00:00|2008-01-05 12:00:00",BookingItem.class),
                convertToObj(       "2|2008-01-05 12:00:00|2008-01-05 16:30:00",ProjectBooking.class),
                convertToObj("Internal|2008-01-05 17:00:00|2008-01-05 18:00:00",BookingItem.class)
                ));

        LinkedList<BookingItem> Sunday = new LinkedList<BookingItem>(Arrays.asList(
                convertToObj("Internal|2008-01-06 07:00:00|2008-01-06 08:00:00",BookingItem.class),
                convertToObj(       "1|2008-01-06 08:00:00|2008-01-06 13:00:00",ProjectBooking.class),
                convertToObj("Internal|2008-01-06 14:00:00|2008-01-06 15:00:00",BookingItem.class),
                convertToObj("Worksho2|2008-01-06 15:00:00|2008-01-06 18:00:00",WorkshopBooking.class)
                ));
  
        //Month            WEEK < DAY < item     >>
        Month = new ArrayList<List<List<BookingItem>>>();
        Month.add(new LinkedList<List<BookingItem>>(Arrays.asList(Monday,Thursday,Wednesday,Thursday,Friday,Saturday,Sunday)));

        
    }
    
    /**
     * Test der Hilfsklassen und -funktionen des UnitTests
     * @throws ParseException 
     */
    @Test
    public void convertToTest() throws ParseException {

        ///////////////     Project    ////////////////////////////////////////////////////
        ProjectBooking ti1 = new ProjectBooking();
        ti1.setStart(dtF.parse("2016-01-01 12:00:00"));
        ti1.setStop(dtF.parse("2016-01-01 13:00:00"));
        ti1.setProject(new Project());
        ti1.getProject().setId(1);
        
        String ti1AsString = convertToString(ti1);
        ProjectBooking ti1AsObj = convertToObj(ti1AsString,ProjectBooking.class);
        String ti1AsString2 = convertToString(ti1AsObj);
        ///////////////////////////////////////////////////////////////////////////////////
        assertTrue(ti1AsString.compareTo(ti1AsString2) == 0);
        ///////////////////////////////////////////////////////////////////////////////////

        
        ///////////////     Vacation    ////////////////////////////////////////////////////
        VacationBooking vi1 = new VacationBooking();
        vi1.setStart(dtF.parse("2016-01-01 12:00:00"));
        vi1.setStop(dtF.parse("2016-01-01 13:00:00"));
        vi1.setStatus(VacationBooking.StatusVacation.VACATION_CONFIRMED.toString());
        
        String vi1AsString = convertToString(vi1);
        VacationBooking vi1AsObj = convertToObj(vi1AsString,VacationBooking.class);
        String vi1AsString2 = convertToString(vi1AsObj);
        ///////////////////////////////////////////////////////////////////////////////////
        assertTrue(vi1AsString.compareTo(vi1AsString2) == 0);
        ///////////////////////////////////////////////////////////////////////////////////

        
        ///////////////     Workshop    ////////////////////////////////////////////////////
        WorkshopBooking wsi1 = new WorkshopBooking();
        wsi1.setStart(dtF.parse("2016-01-01 12:00:00"));
        wsi1.setStop(dtF.parse("2016-01-01 13:00:00"));
        
        String wsi1AsString = convertToString(wsi1);
        WorkshopBooking wsi1AsObj = convertToObj(wsi1AsString,WorkshopBooking.class);
        String wsi1AsString2 = convertToString(wsi1AsObj);
        ///////////////////////////////////////////////////////////////////////////////////
        assertTrue(wsi1AsString.compareTo(wsi1AsString2) == 0);
        ///////////////////////////////////////////////////////////////////////////////////

        
        ///////////////     Intern    ////////////////////////////////////////////////////
        BookingItem bi1 = new ProjectBooking();
        bi1.setStart(dtF.parse("2016-01-01 12:00:00"));
        bi1.setStop(dtF.parse("2016-01-01 13:00:00"));
        
        String bi1AsString = convertToString(bi1);
        BookingItem bi1AsObj = convertToObj(bi1AsString,BookingItem.class);
        String bi1AsString2 = convertToString(bi1AsObj);
        ///////////////////////////////////////////////////////////////////////////////////
        assertTrue(bi1AsString.compareTo(bi1AsString2) == 0);
        ///////////////////////////////////////////////////////////////////////////////////

        
        
    }
    
    @Test
    public void testCompare() {
        CompositeTimeService timeService = new CompositeTimeService();
        
        
    }       

    public void getServiceName() {
    }       

    public void commitTime() {
    }       

    public void combine() {
    }       

    public void getBookingList() {
    }       
    
    
}
