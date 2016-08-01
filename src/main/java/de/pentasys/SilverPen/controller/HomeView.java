package de.pentasys.SilverPen.controller;

import java.util.ArrayList;
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

        curDate = new Date();
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
    
    public void onRowEdit(RowEditEvent event) {
        lg.info("Hove Edit");
        FacesMessage msg = new FacesMessage("Buchung bearbeitet", getDetails((Hour)event.getObject()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        serviceTime.commitTime(curLogin.getCurrentUser(), commitDisplay.get(0));
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
