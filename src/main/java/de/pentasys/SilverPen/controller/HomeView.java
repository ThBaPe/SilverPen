package de.pentasys.SilverPen.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.Hour;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.service.TimeRegisterService;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import java.util.Date;

@Named
@RequestScoped
public class HomeView {

 
    private List<Hour> commitDisplay;
    
    @Inject private TimeRegisterService serviceTime;
    @Inject private LoginInfo curLogin;
    @Inject private Logger lg;
    
    public List<Hour> getCommitDisplay() {
        return commitDisplay;
    }

    public void setCommitDisplay(List<Hour> commitDisplay) {
        this.commitDisplay = commitDisplay;
    }

    @PostConstruct
    public void init() {

        commitDisplay = new ArrayList<Hour>();
        Hour tmp = new Hour();
        tmp.setStart(new Date());
        tmp.setStop(new Date());
        commitDisplay.add(tmp);
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
    
}
