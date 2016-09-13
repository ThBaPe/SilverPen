package de.pentasys.SilverPen.controller;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.service.BookingItemService;
import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.service.ProjectService;
import de.pentasys.SilverPen.service.TimeService;

@Named
@ManagedBean
@RequestScoped
public class BookingItemListView {
    
    @Inject BookingItemService bils;
    @Inject private ProjectService serProj;
    @Inject private BookingItemService serBooking;
    @Inject private LoginInfo curLogin;
    @Inject private Logger lg;

    private List<BookingItem> bookingItems;
    private List<BookingItem> weeklyBookings;
    
    @PostConstruct
    public void init(){
        setBookingItems(bils.getBookingItems(curLogin.getCurrentUser()));
        setWeeklyBookings();
    }

	public List<BookingItem> getBookingItems() {
		return bookingItems;
	}

	public void setBookingItems(List<BookingItem> bookingItems) {
		this.bookingItems = bookingItems;
	}
	
	public List<BookingItem> getWeeklyBookings() {
	    return weeklyBookings;
	}
	
	public void setWeeklyBookings() {

//	    TestCode
//	    List<BookingItem> litems = serProj.getBookingList(curLogin.getCurrentUser(), TimeService.TIME_BOX.DAY, new java.util.Date(), TimeService.SORT_TYPE.START);
//	    lg.info("ProjectItems Day: " + litems.size());
//
//        List<BookingItem> litems2 = serProj.getBookingList(curLogin.getCurrentUser(), TimeService.TIME_BOX.WEEK, new java.util.Date(), TimeService.SORT_TYPE.START);
//        lg.info("ProjectItems Week: " + litems2.size());
//
//        List<BookingItem> litems3 = serProj.getBookingList(curLogin.getCurrentUser(), TimeService.TIME_BOX.MOTH, new java.util.Date(), TimeService.SORT_TYPE.START);
//        lg.info("ProjectItems Month: " + litems3.size());
//
//        List<BookingItem> litems4 = serBooking.getBookingList(curLogin.getCurrentUser(), TimeService.TIME_BOX.DAY, new java.util.Date(), TimeService.SORT_TYPE.START);
//        lg.info("BookingItem Day: " + litems4.size());
//
//        List<BookingItem> litems5 = serBooking.getBookingList(curLogin.getCurrentUser(), TimeService.TIME_BOX.WEEK, new java.util.Date(), TimeService.SORT_TYPE.START);
//        lg.info("BookingItem Week: " + litems5.size());
//        
//        List<BookingItem> litems6 = serBooking.getBookingList(curLogin.getCurrentUser(), TimeService.TIME_BOX.MOTH, new java.util.Date(), TimeService.SORT_TYPE.START);
//        lg.info("BookingItem Month: " + litems6.size());
	    
	    this.weeklyBookings = this.bookingItems;
	    Collections.reverse(this.weeklyBookings);
	    
	    int i = this.weeklyBookings.size()-1;
	   
	    while (i>=0) {	
	        
	        if (this.weeklyBookings.get(i).getWeekDay().equals("Montag") && i < this.weeklyBookings.size()-1 ) {
                if (this.weeklyBookings.get(i+1).getWeekDay().equals("Freitag")) {
                    this.weeklyBookings = this.weeklyBookings.subList(0, i+1);
                }
	        }
	        
	        if (i == this.weeklyBookings.size()-1) {
	            this.weeklyBookings.get(i).setSumHours(this.weeklyBookings.get(i).calculateTime());
	        }
	        else { 
	            double tempSum = this.weeklyBookings.get(i).calculateTime();
	            this.weeklyBookings.get(i).setSumHours(tempSum + this.weeklyBookings.get(i+1).getSumHours());
	        }   
	        i-=1;
	    }
	}
	
}