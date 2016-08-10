package de.pentasys.SilverPen.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.ProjectService;
import de.pentasys.SilverPen.service.UserModService;

@Named
@SessionScoped
public class UserProjView implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private ProjectService projService;
    
    @Inject
    private UserModService ums;
    
    @Inject
    private Logger log;
    
    private DualListModel<Project> projects;
    private List<Project> source;
    private List<Project> target;
    private User user;
    
    @PostConstruct
    public void init(){
        target = new ArrayList<Project>();
        source = new ArrayList<Project>();
        projects = new DualListModel<Project>(source, target);
    }
    
    /**
     * Füllt die Listen der PickList mit den bereits zugewiesenen und noch vorhandenen Rollen
     * 
     * @param username Der Benutzer, dessen Rollen angezeigt werden sollen
     */
    public void fillLists(String username){
        user = ums.findUserInDb(username);
        
        if(!user.getProjects().isEmpty()){
            for(Project proj : user.getProjects()){
                target.add(proj);
            }
            source = fillSource(target);
        } else {
        
            source = projService.getAllProjects();
        }
        
        projects = new DualListModel<Project>(source, target);
    }

    @SuppressWarnings("unchecked")
    public void onTransfer (TransferEvent e){
        List<Project> items = (List<Project>) e.getItems();
        if (e.isAdd()){
            for (Project proj : items){
                target.add(proj);
                source.remove(proj);
            }
        } else {
            for (Project proj : items){
                target.remove(proj);
                source.add(proj);
            }
        }
        log.info("target size is: "+target.size());
    }
    
    public void persist(){
        log.info("Targetlist size: "+target.size()+", sourcelist size: "+source.size());
        projService.persist(user, target, source);
    }
    
    public DualListModel<Project> getProjects() {
        return projects;
    }

    public void setProjects(DualListModel<Project> projects) {
        this.projects = projects;
    }
    
    private List<Project> fillSource(List<Project> target){
        List<Project> result = projService.getAllProjects();
        
        for (Project proj : target){
            int i = result.indexOf(proj);
            result.remove(i);
        }
        
        return result;
    }
}
