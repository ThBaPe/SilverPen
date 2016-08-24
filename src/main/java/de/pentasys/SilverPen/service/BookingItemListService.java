package de.pentasys.SilverPen.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;

@Stateless
public class BookingItemListService {

    @Inject
    EntityManager em;
    
    public List<BookingItem> getBookingItems(User user){
    	
    	TypedQuery<BookingItem> query = em.createNamedQuery(BookingItem.findByUser,BookingItem.class);
    	return query.setParameter("user", user).getResultList();
    }
}
