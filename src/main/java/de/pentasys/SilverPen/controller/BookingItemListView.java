package de.pentasys.SilverPen.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.service.BookingItemListService;

@Named
@ManagedBean
@RequestScoped
public class BookingItemListView {
    
    @Inject
    BookingItemListService bils;
    User user;

    private List<BookingItem> bookingItems;
    
    @PostConstruct
    public void init(){
        setBookingItems(bils.getBookingItems(user));
    }

	public List<BookingItem> getBookingItems() {
		return bookingItems;
	}

	public void setBookingItems(List<BookingItem> bookingItems) {
		this.bookingItems = bookingItems;
	}
}