package de.pentasys.SilverPen.controller;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.service.TimeRegisterService;
import de.pentasys.SilverPen.util.PageNavigationResult;


@Named
@RequestScoped
public class HomeView {

    private java.util.Date curDate;
    private BookingItem curHour;
    private String project;
    private String category;


    @Inject private TimeRegisterService serviceTime;
    @Inject private LoginInfo curLogin;
    @Inject private Logger lg;
 
    @PostConstruct
    public void init() {

        curDate = new Date();
        curHour = new ProjectBooking();
        curHour.setStart(new Date());
        curHour.setStop(new Date());
 
        isAdmin = curLogin.getCurrentUser().hasRole("Admin");
    }
  

    @SuppressWarnings("deprecation")
    public void commitTime() {
        
        lg.info("Hove Edit");
        lg.info("commitTime curDate: " + curDate);

        Date dtStart = curHour.getStart();
        Date dtStop = curHour.getStop();
        
        Date newStart = new Date(curDate.getYear(),curDate.getMonth(),curDate.getDate(),dtStart.getHours(),dtStart.getMinutes(),0);
        Date newStop = new Date(curDate.getYear(),curDate.getMonth(),curDate.getDate(),dtStop.getHours(),dtStop.getMinutes(),0);

        lg.info("commitTime newStart: " + newStart);
        lg.info("commitTime newStop: " + newStop);

        curHour.setStart(newStart);
        curHour.setStop(newStop);
        
        serviceTime.commitTime(curLogin.getCurrentUser(), curHour);
        
    }


    public String logout(){
        curLogin.setCurrentUser(null);
        lg.info("User-State after logout: "+curLogin.getCurrentUser());
        return PageNavigationResult.USER_SIGNIN.toString();
    }
    
    public BookingItem getCurHour() {
        return curHour;
    }

    public void setCurHour(BookingItem curHour) {
        this.curHour = curHour;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    private boolean isAdmin;
    
    public java.util.Date getCurDate() {
        return curDate;
    }

    public void setCurDate(java.util.Date curDate) {
        this.curDate = curDate;
    }
    
    
    public LoginInfo getCurLogin() {
        return curLogin;
    }

    public void setCurLogin(LoginInfo curLogin) {
        this.curLogin = curLogin;
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
