package de.pentasys.SilverPen.controller;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.pentasys.SilverPen.service.TimeService.TIME_BOX;

/**
 * Hilfsklasse für Informationen zur aktuellen Filterung
 * @author bankieth
 *
 */
@SessionScoped
@Named
public class ViewContext implements Serializable{

    private static final long serialVersionUID = 1325749939954722593L;

    /**
     * Datum für den Betrachteten Zeitraum
     * Datum und Zeitraum
     */
    private Date pinDaten;
    private TIME_BOX timeBox;
    
    public Date getPinDaten() {
        return pinDaten;
    }
    public void setPinDaten(Date pinDaten) {
        this.pinDaten = pinDaten;
    }
    public TIME_BOX getTimeBox() {
        return timeBox;
    }
    public void setTimeBox(TIME_BOX timeBox) {
        this.timeBox = timeBox;
    }
    
}
