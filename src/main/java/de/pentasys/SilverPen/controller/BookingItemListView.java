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
import de.pentasys.SilverPen.service.BookingItemListService;
import de.pentasys.SilverPen.service.LoginInfo;

@Named
@ManagedBean
@RequestScoped
public class BookingItemListView {
    
    @Inject BookingItemListService bils;
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
	    this.weeklyBookings = this.bookingItems;
	    Collections.reverse(this.weeklyBookings);
	    
	    int i = 0;
	    while (i<weeklyBookings.size()-1) {
	        lg.info("Weekday: " + this.weeklyBookings.get(i).getWeekDay());
	        if (this.weeklyBookings.get(i).getWeekDay().equals("Montag") ) {
	                if (this.weeklyBookings.get(i+1).getWeekDay().equals("Freitag")) {
	                    this.weeklyBookings = this.weeklyBookings.subList(0, i+1);
	                    break;
	                }
	        }
	        i+=1;
	    }
	}
	
}