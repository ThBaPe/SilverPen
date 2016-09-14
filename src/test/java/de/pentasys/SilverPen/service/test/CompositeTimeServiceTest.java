package de.pentasys.SilverPen.service.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.el.ELProcessor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.runners.MockitoJUnitRunner;

import com.sun.org.glassfish.external.amx.AMXGlassfish.BootAMXCallback;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.model.booking.VacationBooking;
import de.pentasys.SilverPen.model.booking.WorkshopBooking;
import de.pentasys.SilverPen.service.TimeService;
import de.pentasys.SilverPen.service.TimeService.SORT_TYPE;
import de.pentasys.SilverPen.service.TimeService.TIME_BOX;
import de.pentasys.SilverPen.util.CompositeTimeService;

import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CompositeTimeServiceTest {
    
    private static User user1; //< ts1, ts2, ts3, ts4, 
    private static User user2; //< ---, ts2, ts3, ---, 
    private static User user3; //< ---, ---, ts3, ---, 
    private static User user4; //< ts1, ---, ---, ---,  
    private static User user5; //< ---, ts2, ---, ts4,
    private static User user6; //< ---, ---, ---, ---, 
    
    private static TimeService srvProj; //< user1, -----, -----, user4, ----- //< Projektstunden
    private static TimeService srvVaca; //< user1, user2  -----, -----, user5 //< Urlaub
    private static TimeService srvWoSh; //< user1, user2, user3  -----, ----- //< Workshop
    private static TimeService srvInte; //< user1, user2, user3  -----, user5 //< Intern
    
    //             Month < WEEK < DAY > > >
    private static List<List<List<BookingItem>>> DataMonth = new ArrayList<List<List<BookingItem>>>();
    private TestHelper helper = new TestHelper(0, 0);

    
    @Before
    public void setUp() throws Exception {
        
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user4 = new User();
        user5 = new User();

        
        // Statischen Felder initialisieren
        srvProj = mock(TimeService.class);
        srvVaca = mock(TimeService.class);
        srvWoSh = mock(TimeService.class);
        srvInte = mock(TimeService.class);

        
        when(srvProj.getServiceName()).thenReturn("MockTimeServiceProject");
        when(srvVaca.getServiceName()).thenReturn("MockTimeServiceUrlaub");
        when(srvWoSh.getServiceName()).thenReturn("MockTimeServiceWorkShop");
        when(srvInte.getServiceName()).thenReturn("MockTimeServiceIntern");
        


 
        // Aufbau Projekte:         "ProjektID|        Start      |        Stop       "    
        //  -"-   Urlaub:      "StatusVacation|        Start      |        Stop       "    
        //  -"-   Workshop:    "DummyText     |        Start      |        Stop       "    
        //  -"-   Intern:      "DummyText     |        Start      |        Stop       "    
        
        LinkedList<BookingItem> Monday = new LinkedList<BookingItem>(Arrays.asList(
                TestHelper.convertToObj("1|2007-12-31 07:00:00|2007-12-31 09:00:00",ProjectBooking.class),
                TestHelper.convertToObj("1|2007-12-31 09:00:00|2007-12-31 11:00:00",ProjectBooking.class),
                TestHelper.convertToObj("2|2007-12-31 11:00:00|2007-12-31 13:00:00",ProjectBooking.class),
                TestHelper.convertToObj("1|2007-12-31 14:00:00|2007-12-31 16:00:00",ProjectBooking.class),
                TestHelper.convertToObj("2|2007-12-31 16:00:00|2007-12-31 18:00:00",ProjectBooking.class),
                TestHelper.convertToObj("1|2007-12-31 18:00:00|2007-12-31 19:00:00",ProjectBooking.class)));

        LinkedList<BookingItem> Tuesday = new LinkedList<BookingItem>(Arrays.asList(
                TestHelper.convertToObj("VACATION_CONFIRMED|2008-01-01 08:00:00|2008-01-01 16:00:00",VacationBooking.class)
                ));

        LinkedList<BookingItem> Wednesday = new LinkedList<BookingItem>(Arrays.asList(
                TestHelper.convertToObj(                 "1|2008-01-02 07:00:00|2008-01-02 08:00:00",ProjectBooking.class),
                TestHelper.convertToObj("VACATION_CONFIRMED|2008-01-02 08:00:00|2008-01-02 16:00:00",VacationBooking.class)
                ));

        LinkedList<BookingItem> Thursday = new LinkedList<BookingItem>(Arrays.asList(
                TestHelper.convertToObj("Workshop|2008-01-03 07:00:00|2008-01-03 12:00:00",WorkshopBooking.class),
                TestHelper.convertToObj(       "2|2008-01-03 12:00:00|2008-01-03 14:00:00",ProjectBooking.class),
                TestHelper.convertToObj("Workshop|2008-01-03 14:00:00|2008-01-03 19:00:00",WorkshopBooking.class)
                ));

        LinkedList<BookingItem> Friday = new LinkedList<BookingItem>(Arrays.asList(
                TestHelper.convertToObj(       "2|2008-01-04 07:00:00|2008-01-04 10:00:00",ProjectBooking.class),
                TestHelper.convertToObj(       "3|2008-01-04 10:00:00|2008-01-04 13:00:00",ProjectBooking.class),
                TestHelper.convertToObj("Internal|2008-01-04 13:00:00|2008-01-04 14:00:00",BookingItem.class),
                TestHelper.convertToObj(       "2|2008-01-04 14:00:00|2008-01-04 18:00:00",ProjectBooking.class),
                TestHelper.convertToObj(       "1|2008-01-04 18:00:00|2008-01-04 19:00:00",ProjectBooking.class)
                ));

        LinkedList<BookingItem> Saturday = new LinkedList<BookingItem>(Arrays.asList(
                TestHelper.convertToObj("Worksho2|2008-01-05 08:00:00|2008-01-05 10:00:00",WorkshopBooking.class),
                TestHelper.convertToObj("Internal|2008-01-05 10:00:00|2008-01-05 12:00:00",BookingItem.class),
                TestHelper.convertToObj(       "2|2008-01-05 12:00:00|2008-01-05 16:30:00",ProjectBooking.class),
                TestHelper.convertToObj("Internal|2008-01-05 17:00:00|2008-01-05 18:00:00",BookingItem.class)
                ));

        LinkedList<BookingItem> Sunday = new LinkedList<BookingItem>(Arrays.asList(
                TestHelper.convertToObj("Internal|2008-01-06 07:00:00|2008-01-06 08:00:00",BookingItem.class),
                TestHelper.convertToObj(       "1|2008-01-06 08:00:00|2008-01-06 13:00:00",ProjectBooking.class),
                TestHelper.convertToObj("Internal|2008-01-06 14:00:00|2008-01-06 15:00:00",BookingItem.class),
                TestHelper.convertToObj("Worksho2|2008-01-06 15:00:00|2008-01-06 18:00:00",WorkshopBooking.class)
                ));
  
        //Month            WEEK < DAY < item     >>
        DataMonth = new ArrayList<List<List<BookingItem>>>();
        DataMonth.add(new LinkedList<List<BookingItem>>(Arrays.asList(Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday)));
        
        List<BookingItem> Week = new LinkedList<>();
        Week.addAll(DataMonth.get(helper.INITIAL_WEEK).get(helper.INITIAL_DAY  ));
        Week.addAll(DataMonth.get(helper.INITIAL_WEEK).get(helper.INITIAL_DAY+1));
        Week.addAll(DataMonth.get(helper.INITIAL_WEEK).get(helper.INITIAL_DAY+2));
        Week.addAll(DataMonth.get(helper.INITIAL_WEEK).get(helper.INITIAL_DAY+3));
        Week.addAll(DataMonth.get(helper.INITIAL_WEEK).get(helper.INITIAL_DAY+4));
        Week.addAll(DataMonth.get(helper.INITIAL_WEEK).get(helper.INITIAL_DAY+6));

        GregorianCalendar calHelper = new GregorianCalendar();
        List<BookingItem> Month = new LinkedList<>();
        Month.addAll(Week);
        
        for(int i = 1; i < 5; i++){
            for (BookingItem item : Week) {
                
                BookingItem newItem = null;
                
                // Deep Cloning/Copy
                if (item instanceof ProjectBooking 
                        && ((ProjectBooking) item).getProject() == null) {
    
                    newItem = TestHelper.convertToObj(TestHelper.convertToString(item),BookingItem.class);
    
                }else if(item instanceof ProjectBooking 
                        && ((ProjectBooking) item).getProject() != null){
    
                    newItem = TestHelper.convertToObj(TestHelper.convertToString((ProjectBooking)item),ProjectBooking.class);
    
                }else if(item instanceof WorkshopBooking) {
    
                    newItem = TestHelper.convertToObj(TestHelper.convertToString((WorkshopBooking)item),WorkshopBooking.class);
                    
                }else if(item instanceof VacationBooking) {
    
                    newItem = TestHelper.convertToObj(TestHelper.convertToString((VacationBooking)item),VacationBooking.class);
                }
    
                // Buchung um eine Woche verschieben
                calHelper.setTime(newItem.getStart());
                calHelper.add(Calendar.DATE, 7*i);
                newItem.setStart(calHelper.getTime());
    
                calHelper.setTime(newItem.getStop());
                calHelper.add(Calendar.DATE, 7*i);
                newItem.setStop(calHelper.getTime());
    
                Month.add(newItem);
                
            }
        }        

        ////////////// ProjektStunden /////////////////
        Predicate<BookingItem> isProject = new Predicate<BookingItem>() {
            @Override
            public boolean test(BookingItem item) {
                return item instanceof ProjectBooking && ((ProjectBooking) item).getProject() != null;
            }
        };

        
        when(srvProj.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.DAY), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(DataMonth.get(helper.INITIAL_WEEK).get(helper.INITIAL_DAY),isProject)); 

        when(srvProj.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.WEEK), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(Week,isProject));

        when(srvProj.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.MOTH), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(Month,isProject));

        
        ////////////// Urlaub         /////////////////
        Predicate<BookingItem> isVacation = new Predicate<BookingItem>() {
            @Override
            public boolean test(BookingItem item) {
                return item instanceof VacationBooking;
            }
        };
        
        when(srvVaca.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.DAY), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(DataMonth.get(helper.INITIAL_WEEK).get(helper.INITIAL_DAY),isVacation)); 

        when(srvVaca.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.WEEK), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(Week,isVacation));

        when(srvVaca.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.MOTH), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(Month,isVacation));

        ////////////// Workshop       /////////////////
        Predicate<BookingItem> isWorkShop = new Predicate<BookingItem>() {
            @Override
            public boolean test(BookingItem item) {
                return item instanceof WorkshopBooking;
            }
        };
        
        when(srvWoSh.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.DAY), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(DataMonth.get(helper.INITIAL_WEEK).get(helper.INITIAL_DAY),isWorkShop)); 

        when(srvWoSh.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.WEEK), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(Week,isWorkShop));

        when(srvWoSh.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.MOTH), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(Month,isWorkShop));


        ////////////// Intern         /////////////////
        Predicate<BookingItem> isInternal = new Predicate<BookingItem>() {
            @Override
            public boolean test(BookingItem item) {
                return item instanceof ProjectBooking && ((ProjectBooking) item).getProject() == null;
            }
        };
        
        when(srvInte.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.DAY), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(DataMonth.get(helper.INITIAL_WEEK).get(helper.INITIAL_DAY),isInternal)); 

        when(srvInte.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.WEEK), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(Week,isInternal));

        when(srvInte.getBookingList(Matchers.eq(user1), Matchers.eq(TIME_BOX.MOTH), Matchers.any(Date.class), Matchers.any(SORT_TYPE.class)))
                .thenReturn(TestHelper.filterListItem(Month,isInternal));

    }
    
    /**
     * Test der Hilfsklassen und -funktionen des UnitTests
     * @throws ParseException 
     */
    @Test
    public void convertToTest() throws ParseException {

        ///////////////     Project    ////////////////////////////////////////////////////
        ProjectBooking ti1 = new ProjectBooking();
        ti1.setStart(TestHelper.dtF.parse("2016-01-01 12:00:00"));
        ti1.setStop(TestHelper.dtF.parse("2016-01-01 13:00:00"));
        ti1.setProject(new Project());
        ti1.getProject().setId(1);
        
        String ti1AsString = TestHelper.convertToString(ti1);
        ProjectBooking ti1AsObj = TestHelper.convertToObj(ti1AsString,ProjectBooking.class);
        String ti1AsString2 = TestHelper.convertToString(ti1AsObj);
        ///////////////////////////////////////////////////////////////////////////////////
        assertTrue(ti1AsString.compareTo(ti1AsString2) == 0);
        ///////////////////////////////////////////////////////////////////////////////////

        
        ///////////////     Vacation    ////////////////////////////////////////////////////
        VacationBooking vi1 = new VacationBooking();
        vi1.setStart(TestHelper.dtF.parse("2016-01-01 12:00:00"));
        vi1.setStop(TestHelper.dtF.parse("2016-01-01 13:00:00"));
        vi1.setStatus(VacationBooking.StatusVacation.VACATION_CONFIRMED.toString());
        
        String vi1AsString = TestHelper.convertToString(vi1);
        VacationBooking vi1AsObj = TestHelper.convertToObj(vi1AsString,VacationBooking.class);
        String vi1AsString2 = TestHelper.convertToString(vi1AsObj);
        ///////////////////////////////////////////////////////////////////////////////////
        assertTrue(vi1AsString.compareTo(vi1AsString2) == 0);
        ///////////////////////////////////////////////////////////////////////////////////

        
        ///////////////     Workshop    ////////////////////////////////////////////////////
        WorkshopBooking wsi1 = new WorkshopBooking();
        wsi1.setStart(TestHelper.dtF.parse("2016-01-01 12:00:00"));
        wsi1.setStop(TestHelper.dtF.parse("2016-01-01 13:00:00"));
        
        String wsi1AsString = TestHelper.convertToString(wsi1);
        WorkshopBooking wsi1AsObj = TestHelper.convertToObj(wsi1AsString,WorkshopBooking.class);
        String wsi1AsString2 = TestHelper.convertToString(wsi1AsObj);
        ///////////////////////////////////////////////////////////////////////////////////
        assertTrue(wsi1AsString.compareTo(wsi1AsString2) == 0);
        ///////////////////////////////////////////////////////////////////////////////////

        
        ///////////////     Intern    ////////////////////////////////////////////////////
        BookingItem bi1 = new ProjectBooking();
        bi1.setStart(TestHelper.dtF.parse("2016-01-01 12:00:00"));
        bi1.setStop(TestHelper.dtF.parse("2016-01-01 13:00:00"));
        
        String bi1AsString = TestHelper.convertToString(bi1);
        BookingItem bi1AsObj = TestHelper.convertToObj(bi1AsString,BookingItem.class);
        String bi1AsString2 = TestHelper.convertToString(bi1AsObj);
        ///////////////////////////////////////////////////////////////////////////////////
        assertTrue(bi1AsString.compareTo(bi1AsString2) == 0);
        ///////////////////////////////////////////////////////////////////////////////////

        
        
    }
    
    @Test
    public void getBookingList() {
         
        List<List<TimeService>> lServiceComb = new LinkedList<List<TimeService>>();
        lServiceComb.add(Arrays.asList( srvProj                             ));
        lServiceComb.add(Arrays.asList(         srvInte                     ));
        lServiceComb.add(Arrays.asList(                 srvVaca             ));
        lServiceComb.add(Arrays.asList(                         srvWoSh     ));
        lServiceComb.add(Arrays.asList( srvProj,srvInte                     ));
        lServiceComb.add(Arrays.asList(         srvInte,srvVaca             ));
        lServiceComb.add(Arrays.asList(                 srvVaca,srvWoSh     ));
        lServiceComb.add(Arrays.asList( srvProj,        srvVaca             ));
        lServiceComb.add(Arrays.asList(         srvInte,        srvWoSh     ));
        lServiceComb.add(Arrays.asList( srvProj,                srvWoSh     ));
        lServiceComb.add(Arrays.asList( srvProj,srvInte,srvVaca,srvWoSh     ));
        
        for (List<TimeService> combination : lServiceComb) {
            
            CompositeTimeService comp = new CompositeTimeService();

            for (TimeService srvTime : combination) {
                comp.add(srvTime);
            }                

            System.out.println(comp.getServiceName());
            
            LinkedList<List<BookingItem>> checkList = new LinkedList<List<BookingItem>>();
            checkList.add(comp.getBookingList(user1, TIME_BOX.DAY, new Date(), SORT_TYPE.START));
            checkList.add(comp.getBookingList(user1, TIME_BOX.WEEK, new Date(), SORT_TYPE.START));
            checkList.add(comp.getBookingList(user1, TIME_BOX.MOTH, new Date(), SORT_TYPE.START));
    
            for (List<BookingItem> list : checkList) {

                System.out.println("Index of CheckList : " + checkList.indexOf(list));
                
                if(list.isEmpty()) {
                    continue;
                }
                
                java.util.ListIterator<BookingItem> cur = list.listIterator();
                BookingItem last = cur.next();
                System.out.println(TestHelper.convertToString(last));
    
                while(cur.hasNext()) {
                    BookingItem next = cur.next();
                    System.out.println(TestHelper.convertToString(next));
                    assertTrue(last.getStart().getTime() <= next.getStart().getTime());
                    last = next;
                }
            }
        }
    }       

    public void getServiceName() {
    }       

    public void commitTime() {
    }       
    
}
