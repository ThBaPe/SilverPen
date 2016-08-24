package de.pentasys.SilverPen.controller;

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
    
    @PostConstruct
    public void init(){
        setBookingItems(bils.getBookingItems(curLogin.getCurrentUser()));
        lg.info("hi");
        lg.info("Anzahl B-Items: " + getBookingItems().size());
    }

	public List<BookingItem> getBookingItems() {
		return bookingItems;
	}

	public void setBookingItems(List<BookingItem> bookingItems) {
		this.bookingItems = bookingItems;
	}
}