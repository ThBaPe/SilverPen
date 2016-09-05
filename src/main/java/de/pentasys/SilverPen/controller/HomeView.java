package de.pentasys.SilverPen.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.service.ProjectService;
import de.pentasys.SilverPen.service.TimeRegisterService;
import de.pentasys.SilverPen.util.PageNavigationResult;


@Named
@RequestScoped
public class HomeView {

    private java.util.Date curDate;
    private ProjectBooking curHour;
    private String projectID;
    private String category;
    private Map<String, String> projects;


    public Map<String, String> getProjects() {
        return projects;
    }


    public void setProjects(Map<String, String> projects) {
        this.projects = projects;
    }


    @Inject private TimeRegisterService serviceTime;
    @Inject private LoginInfo curLogin;
    @Inject private Logger lg;
    @Inject private BookingItemListView bookings;
    @Inject private ProjectService serProj;
 
    @PostConstruct
    public void init() {

        curDate = new Date();
        curHour = new ProjectBooking();
        curHour.setStart(new Date());
        curHour.setStop(new Date());
 
        isAdmin = curLogin.getCurrentUser().hasRole("Admin");
        
        bookings.init();
        
        projects = new HashMap<String,String>();
        Collection<Project> userProj = serProj.getUserProjects(curLogin.getCurrentUser().getEmail());
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
        
        int iProjID = Integer.parseInt(projectID);
        
        if(iProjID > 0) {
            // Es wird auf ein Projekt gebucht
            serProj.commitTime(iProjID, curLogin.getCurrentUser(), curHour);
        } else {
            // Es werden Stunden gebucht, bei denen kein Projekt ausgewählt wurde
            serviceTime.commitTime(curLogin.getCurrentUser(), curHour);
        }
        
        this.init();
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
