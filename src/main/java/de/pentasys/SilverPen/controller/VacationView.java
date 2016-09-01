package de.pentasys.SilverPen.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.booking.VacationBooking;
import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.service.VacationService;
import de.pentasys.SilverPen.util.PageNavigationResult;

@Named
@SessionScoped
public class VacationView implements Serializable {

    @Inject
    private Logger lg;
    @Inject
    private LoginInfo curLogin;
    @Inject
    private VacationService vs;
    /**
     * 
     */
    private static final long serialVersionUID = 597846602482303214L;
    private java.util.Date vacationStart;
    private java.util.Date vacationEnd;
    private Boolean showRemoveBtn;

    public java.util.Date getVacationEnd() {
        return vacationEnd;
    }

    public void setVacationEnd(java.util.Date vacationEnd) {
        this.vacationEnd = vacationEnd;
    }

    public java.util.Date getVacationStart() {
        return vacationStart;
    }

    public void setVacationStart(java.util.Date vacationStart) {
        this.vacationStart = vacationStart;
    }

    public Boolean getShowRemoveBtn() {
        return showRemoveBtn;
    }

    public void setShowRemoveBtn(Boolean showRemoveBtn) {
        this.showRemoveBtn = showRemoveBtn;
    }

    @PostConstruct
    public void init() {
        showRemoveBtn = false;
        vacationStart = new Date();
        vacationEnd = new Date();
    }

    public String addVacation() {
        DateFormat df = new SimpleDateFormat("dd.MM.yy");

        VacationBooking vac = new VacationBooking();
        vac.setStart(vacationStart);
        vac.setStop(vacationEnd);
        vac.setUser(curLogin.getCurrentUser());
        vac.setStatus(VacationBooking.StatusVacation.VACATION_REQUESTED.toString());
        vs.addVacation(vac);
        
        // Urlaubsantrag gleich genehmigen
        vs.confirmVacation(vac);
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Es wurde ein Urlaubsantrag gestellt vom " + df.format(vacationStart) + " bis " + df.format(vacationEnd), null));
        context.getExternalContext().getFlash().setKeepMessages(true);
        init();
        return PageNavigationResult.USER_HOME.toString();
    }

}
