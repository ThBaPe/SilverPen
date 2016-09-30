package de.pentasys.SilverPen.controller;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import de.pentasys.SilverPen.model.Workshop;
import de.pentasys.SilverPen.model.WorkshopParticipant.WorkshopRole;
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
        lWorkShopParticipated = new LinkedList<>();

        lg.info("Start Init");
        lWorkShop = srv.listWorkshops();
        lWorkShopParticipated = srv.listWorkshops(curLogin.getCurrentUser(),WorkshopRole.PARTICIPANT);
        lg.info("WS List: " + lWorkShop.size());
        lg.info("WS_P List: " + lWorkShopParticipated.size());
    
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
    
    public void onRowSelect(SelectEvent event) {
        Workshop ws = (Workshop) event.getObject();
        srv.addPartizipant(ws, curLogin.getCurrentUser());
    }
 
    public void onRowUnselect(UnselectEvent event) {
        Workshop ws = (Workshop) event.getObject();
        srv.remvoedPartizipant(ws, curLogin.getCurrentUser());
    }

}
