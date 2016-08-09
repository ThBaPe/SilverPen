package de.pentasys.SilverPen.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.ProjectService;
import de.pentasys.SilverPen.service.UserModService;

@Named
@RequestScoped
public class UserProjView {
    
    @Inject
    private ProjectService projService;
    
    @Inject
    private UserModService ums;
    
    private DualListModel<Project> projects;
    private List<Project> source;
    private List<Project> target;
    
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
        User user = ums.findUserInDb(username);
        
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
