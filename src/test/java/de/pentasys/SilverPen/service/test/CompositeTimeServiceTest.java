package de.pentasys.SilverPen.service.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.service.CompositeTimeService;
import de.pentasys.SilverPen.service.TimeService;
import de.pentasys.SilverPen.service.TimeService.SORT_TYPE;
import de.pentasys.SilverPen.service.TimeService.TIME_BOX;


@RunWith(MockitoJUnitRunner.class)
public class CompositeTimeServiceTest {
    
    private static User user1; //< ts1, ts2, ts3
    private static User user2; //<      ts2, ts3
    private static User user3; //<           ts3
    private static User user4; //< ts1
    private static User user5; //< -------------
    
    private static TimeService ts1; //< user1,              user4  //< Projektstunden
    private static TimeService ts2; //< user1, user2               //< Urlaub
    private static TimeService ts3; //< user1, user2, user3        //< Workshop

    
    private static SimpleDateFormat dtF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private static String convertToString(BookingItem boocking) {
        return "<booking>" +  dtF.format(boocking.getStart()) + "|" + dtF.format(boocking.getStop()) + "</booking>";
    }
    
    private static String convertToString(ProjectBooking boocking) {
        return "<proj>" + boocking.getProject().getId() + "|" + convertToString((BookingItem)boocking) + "</proj>";
    }

    private static ProjectBooking convertToObj(String boocking) throws ParseException {
        String proj = boocking.substring(6, boocking.length()-7);
        int projID = Integer.parseInt(proj.substring(0,proj.indexOf("|")));
        String book = proj.substring(proj.indexOf("<booking>")+9,proj.indexOf("</booking>"));
        Date start = dtF.parse(book.substring(0,book.indexOf("|")));
        Date stop = dtF.parse(book.substring(book.indexOf("|")+1,book.length()-1));
        
        ProjectBooking curElement = new ProjectBooking();
        curElement.setStart(start);
        curElement.setStop(stop);
        curElement.setProject(new Project());
        curElement.getProject().setId(projID);
        return curElement;

    }

    
    
    @Before
    public void setUp() throws Exception {
        
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user4 = new User();

        
        // Statischen Felder initialisieren
        ts1 = mock(TimeService.class);
        ts2 = mock(TimeService.class);
        ts3 = mock(TimeService.class);
        
        when(ts1.getServiceName()).thenReturn("MockTimeService1");
        when(ts2.getServiceName()).thenReturn("MockTimeService2");
        when(ts3.getServiceName()).thenReturn("MockTimeService3");
        
        

        List<BookingItem> user1ts1 = new ArrayList<BookingItem>();
        
        ProjectBooking ti1 = new ProjectBooking();
        ti1.setStart(dtF.parse("2016-01-01 12:00:00"));
        ti1.setStop(dtF.parse("2016-01-01 13:00:00"));
        ti1.setProject(new Project());
        ti1.getProject().setId(1);
        
        String ti1AsString = convertToString(ti1);
        
        ProjectBooking ti1AsObj = convertToObj(ti1AsString);

        
        
       // when(ts1.getBookingList(user1, Mockito.any(TIME_BOX.class), Mockito.any(Date.class), Mockito.any(SORT_TYPE.class))).thenReturn();

        
        
        
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
