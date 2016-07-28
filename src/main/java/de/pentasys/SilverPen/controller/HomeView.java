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

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import java.util.Date;

@Named
@RequestScoped
public class HomeView {

 
    private List<Hour> commitDisplay;
    private Date date1;
    private java.sql.Timestamp date2;
    
    public java.sql.Timestamp getDate2() {
        return date2;
    }

    public void setDate2(java.sql.Timestamp date2) {
        this.date2 = date2;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public List<Hour> getCommitDisplay() {
        return commitDisplay;
    }

    public void setCommitDisplay(List<Hour> commitDisplay) {
        this.commitDisplay = commitDisplay;
    }

    @PostConstruct
    public void init() {

        date1 = new Date();
        commitDisplay = new ArrayList<Hour>();
        Hour tmp = new Hour();
        tmp.setDescription("aass");
        tmp.setStart(new Timestamp(date1.getTime()));
        tmp.setStop(new Timestamp(date1.getTime()));
        commitDisplay.add(tmp);
    }
    

    
    private String getDetails(Hour element) {
        return "S: " + element.getStart() + "\nE: " + element.getStop() + "\nTXT: " + element.getDescription();
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Buchung bearbeitet", getDetails((Hour)event.getObject()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
