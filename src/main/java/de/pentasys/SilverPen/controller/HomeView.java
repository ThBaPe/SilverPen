package de.pentasys.SilverPen.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.service.BookingItemService;
import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.service.ProjectService;
import de.pentasys.SilverPen.util.PageNavigationResult;


@Named
@RequestScoped
public class HomeView {

    private java.util.Date curDate;
    private ProjectBooking curHour;
    private String projectID;
    private String category;
    private Map<String, String> projects;
    private Date curSelected;

    public Date getCurSelected() {
        return curSelected;
    }


    public void setCurSelected(Date curSelected) {
        this.curSelected = curSelected;
    }


    public Map<String, String> getProjects() {
        return projects;
    }


    public void setProjects(Map<String, String> projects) {
        this.projects = projects;
    }


    @Inject private BookingItemService serviceTime;
    @Inject private LoginInfo curLogin;
    @Inject private ViewContext curContext;
    @Inject private Logger lg;
    @Inject private BookingItemListView bookings;
    @Inject private ProjectService serProj;
 
    @PostConstruct
    public void init() {

        if(curDate == null) {
            curDate = new Date();

            curHour = new ProjectBooking();
            curHour.setStart(new Date());
            curHour.setStop(new Date());
        }
        
        isAdmin = curLogin.getCurrentUser().hasRole("Admin");
        
        bookings.init();
        
        projects = new HashMap<String,String>();
        List<Project> userProj = serProj.getUserProjects(curLogin.getCurrentUser());
        for (Project pro : userProj) {
            projects.put(pro.getName(), String.valueOf(pro.getId()));
        }
        
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

        lg.info("CurSel Proj: " + projectID);
        lg.info("projects List: " + projects.toString());
        lg.info("Find ProjObj: " + projects.get(projectID));
        
        if(projectID.equals("0")) {
            // Es werden Stunden gebucht, bei denen kein Projekt ausgewählt wurde
            serviceTime.commitTime(curLogin.getCurrentUser(), curHour);
        } else {
            // Es wird auf ein Projekt gebucht
            serProj.commitTime(curLogin.getCurrentUser(), curHour, projectID);
        }
        
        this.init();
    }

    /**
     * Datumswechsel verarbeiten
     */
    public void handleDateSelect(SelectEvent event){
        lg.info(" Date selection changed:" + curContext.getPinDaten() + " (old)");
        curContext.setPinDaten((Date) event.getObject());
        lg.info(" Date selection changed:" + curContext.getPinDaten() + " (new)");
        init();
    }

    public String logout(){
        curLogin.setCurrentUser(null);
        lg.info("User-State after logout: "+curLogin.getCurrentUser());
        return PageNavigationResult.USER_SIGNIN.toString();
    }
    
    public ProjectBooking getCurHour() {
        return curHour;
    }

    public void setCurHour(ProjectBooking curHour) {
        this.curHour = curHour;
    }

    public String getProject() {
        return projectID;
    }

    public void setProject(String project) {
        this.projectID = project;
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
