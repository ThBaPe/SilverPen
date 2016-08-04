package de.pentasys.SilverPen.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;


import de.pentasys.SilverPen.model.Hour;
import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.service.TimeRegisterService;


@Named
@RequestScoped
public class HomeView {

    private java.util.Date curDate;
    private java.util.Date curStart;
    private java.util.Date curStop;

    private Hour curHour;
    private String project;
    private String category;
    
    
    
    public java.util.Date getCurStart() {
        return curStart;
    }

    public void setCurStart(java.util.Date curStart) {
        this.curStart = curStart;
    }

    public java.util.Date getCurStop() {
        return curStop;
    }

    public void setCurStop(java.util.Date curStop) {
        this.curStop = curStop;
    }

    public Hour getCurHour() {
        return curHour;
    }

    public void setCurHour(Hour curHour) {
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
  

    @Inject private TimeRegisterService serviceTime;
    @Inject private LoginInfo curLogin;
    @Inject private Logger lg;
    
    
    
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

    @PostConstruct
    public void init() {

        curDate = new Date();
        curStart = new Date();
        curHour = new Hour();
        curHour.setStart(new Date());
        curHour.setStop(new Date());
//      commitDisplay = new Hour();
//      curDate = new Date();

//      commitDisplay.setStart(curDate);
//      commitDisplay.setStop(curDate);
 
        isAdmin = curLogin.getCurrentUser().hasRole("Admin");
    }
  
    private String getDetails(Hour element) {
        return "S: " + element.getStart() + "\nE: " + element.getStop() + "\nTXT: " + element.getDescription();
    }
    
    

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
    
//    public void dateChange(DateSelectEvent event) {
//        
//        this.curDate = event.getDate();
//        this.curDate2 = event.getDate();
//
//        System.out.println("File Date: " + curDate);
//        System.out.println("Hello... I am in DateChange");
//    }

    public void onRowEdit(RowEditEvent event) {
//        lg.info("Hove Edit");
//        FacesMessage msg = new FacesMessage("Buchung bearbeitet", getDetails((Hour)event.getObject()));
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        lg.info("onRowEdit curDate: " + curDate);
//        lg.info("onRowEdit curDate2: " + curDate2);
//        
//        Hour commitTime = commitDisplay.get(0);
//        Date dtStart = commitTime.getStart();
//        Date dtStop = commitTime.getStop();
//        
//        commitTime.setStart(new Date(curDate.getYear(),curDate.getMonth(),curDate.getDay(),dtStart.getHours(),dtStart.getMinutes(),0));
//        commitTime.setStop(new Date(curDate.getYear(),curDate.getMonth(),curDate.getDay(),dtStop.getHours(),dtStop.getMinutes(),0));
//        
//        serviceTime.commitTime(curLogin.getCurrentUser(), commitTime);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Buchung abgebrochen",  getDetails((Hour)event.getObject()));
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public String registerUser(){
        return "admin_signup.xhtml";
    }
    
    public String logout(){
        curLogin.setCurrentUser(null);
        lg.info("User-State after logout: "+curLogin.getCurrentUser());
        return "signin.xhtml?faces-redirect=true";
    }
    
}
