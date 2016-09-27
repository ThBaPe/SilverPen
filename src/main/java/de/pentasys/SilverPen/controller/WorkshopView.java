package de.pentasys.SilverPen.controller;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.Workshop;
import de.pentasys.SilverPen.model.WorkshopParticipant;
import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.service.WorkshopService;



@Named
@RequestScoped
public class WorkshopView implements Serializable{

    private static final long serialVersionUID = 6372521386454586534L;

    private List<Workshop> lWorkShop;
    private List<Workshop> lWorkShopParticipated;


    @Inject private Logger lg;
    @Inject private WorkshopService srv;
    @Inject private LoginInfo curLogin;
    
    @PostConstruct
    public void init(){
        
        lWorkShopParticipated = new LinkedList<>();
        lg.info("Start Init");
        lWorkShop = srv.listWorkshops();
        lg.info("WS List: " + lWorkShop.size());
        
        
        // Prüfen in welchem Workshop der akt. angemeldete Benutzer eingetragen ist
        for (Workshop ws : lWorkShop) {
            
            if(ws.getParticipant() != null) {
                
                for (WorkshopParticipant part : ws.getParticipant()) {
                    if(part.getUsers() != null
                            && part.getUsers() == curLogin.getCurrentUser()){
                        lg.info("Start Init");
                        lWorkShopParticipated.add(ws);
                        break;
                    }
                }
            }
        }
    }

    public List<Workshop> getlWorkShop() {
        return lWorkShop;
    }

    public void setlWorkShop(List<Workshop> lWorkShop) {
        this.lWorkShop = lWorkShop;
    }
    
    public List<Workshop> getlWorkShopParticipated() {
        return lWorkShopParticipated;
    }

    public void setlWorkShopParticipated(List<Workshop> lWorkShopParticipated) {
        this.lWorkShopParticipated = lWorkShopParticipated;
    }

}
