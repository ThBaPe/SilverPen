package de.pentasys.SilverPen.controller;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.Workshop;
import de.pentasys.SilverPen.service.WorkshopService;

@Named
@RequestScoped
public class WorkshopCreateView {

    @Inject
    WorkshopService ws;
    
    private Workshop workshop;
    private String leader;
    private String operator;
    private String location;
    private String name;
    private Date dateFrom;
    private Date dateTo;
    private String description;
    private String state;
    private int maximum;
    
    
/*    public void onDateSelect(SelectEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }*/

    
    public String addWorkshop(){
        workshop = new Workshop();
        workshop.setDescription(description);
        workshop.setLocation(location);
        workshop.setMaxParticipants(maximum);
        workshop.setOrganizer(operator);
        workshop.setStart(dateFrom);
        workshop.setStatus(state);
        workshop.setStop(dateTo);
        workshop.setTitle(name);
        workshop.setTutor(leader);
        
        ws.createWorkshop(workshop);
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Workshop " +name.toUpperCase()+ " erfolgreich angelegt!", null));
        context.getExternalContext().getFlash().setKeepMessages(true);
        
        return "/secure/home.xhtml?faces-redirect=true";
        
    }
    
    public Date getDateFrom() {
        return dateFrom;
    }
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }
    public Date getDateTo() {
        return dateTo;
    }
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
    public String getLeader() {
        return leader;
    }
    public void setLeader(String leader) {
        this.leader = leader;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public int getMaximum() {
        return maximum;
    }
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }
}
