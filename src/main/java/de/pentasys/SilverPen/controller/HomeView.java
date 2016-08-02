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

 
    private List<Hour> commitDisplay;
    private java.util.Date curDate;
    
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

    public List<Hour> getCommitDisplay() {
        return commitDisplay;
    }

    public void setCommitDisplay(List<Hour> commitDisplay) {
        this.commitDisplay = commitDisplay;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @PostConstruct
    public void init() {

        if(curDate == null) {
            curDate = new Date();
        }
        commitDisplay = new ArrayList<Hour>();
        Hour tmp = new Hour();
        tmp.setStart(curDate);
        tmp.setStop(curDate);
        commitDisplay.add(tmp);
        
        isAdmin = curLogin.getCurrentUser().hasRole("Admin");
    }
  
    private String getDetails(Hour element) {
        return "S: " + element.getStart() + "\nE: " + element.getStop() + "\nTXT: " + element.getDescription();
    }
    
    
    @SuppressWarnings("deprecation")
    public void commitTime()
    {
        lg.info("Hove Edit");
        lg.info("commitTime curDate: " + curDate);

        
        Hour commitTime = commitDisplay.get(0);
        Date dtStart = commitTime.getStart();
        Date dtStop = commitTime.getStop();
        lg.info("commitTime dtStart: " + dtStart);
        lg.info("commitTime dtStop: " + dtStop);

        Date newStart = new Date(curDate.getYear(),curDate.getMonth(),curDate.getDate(),dtStart.getHours(),dtStart.getMinutes(),0);
        Date newStop = new Date(curDate.getYear(),curDate.getMonth(),curDate.getDate(),dtStop.getHours(),dtStop.getMinutes(),0);
        
        lg.info("commitTime newStart: " + newStart);
        lg.info("commitTime newStop: " + newStop);
        

        commitTime.setStart(newStart);
        commitTime.setStop(newStop);
        
        serviceTime.commitTime(curLogin.getCurrentUser(), commitTime);
        
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
    
}
