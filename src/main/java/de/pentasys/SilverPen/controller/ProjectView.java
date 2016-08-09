package de.pentasys.SilverPen.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.service.ProjectService;



@Named
@SessionScoped
public class ProjectView implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 597846602482303214L;
    private String customerLabel;
    private java.util.Date projectStart;
    private List<Project> projectList;
    private Project projectSelection;
    private Boolean showRemoveBtn;

    public Boolean getShowRemoveBtn() {
        return showRemoveBtn;
    }

    public void setShowRemoveBtn(Boolean showRemoveBtn) {
        this.showRemoveBtn = showRemoveBtn;
    }

    public void setProjectSelection(Project projectSelection) {
        this.projectSelection = projectSelection;
    }

    public Project getProjectSelection() {
        return projectSelection;
    }

    @Inject private Logger lg;
    @Inject private ProjectService ps;
    
    @PostConstruct
    public void init(){
        projectSelection = null;
        showRemoveBtn = false;
        projectList = ps.getAllProjects();
        projectStart = new Date();
    }

    public String getCustomerLabel() {
        return customerLabel;
    }
    
    public void setCustomerLabel(String customerLabel) {
        this.customerLabel = customerLabel;
    }

    public java.util.Date getProjectStart() {
        return projectStart;
    }

    public void setProjectStart(java.util.Date projectStart) {
        this.projectStart = projectStart;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
    
    public void addProject()
    {
        DateFormat df = new SimpleDateFormat("yyddMM");
        String newProject = "P" + df.format(projectStart) + "." + customerLabel;

        Project pro = new Project();
        pro.setName(newProject);
        pro.setProjectnumber(newProject);
        
        ps.addProject(pro);
        init();
    }

    public void onSelect(SelectEvent event) {

        Project pro = (Project) event.getObject();
        projectSelection = pro;
        showRemoveBtn = true;
        lg.info("onSelect: " + pro);
    }
    
    public void clearProjekt()
    {
//        if(!curSelProject.isEmpty()){
//            int iListSize = projectList.size();
//            for (int i = 0; i < iListSize; i++) {
//                if(projectList.get(i) == curSelProject)
//                {
//                    lg.info("Projekt Remove: " + curSelProject);
//                    projectList.remove(i);
//                    break;
//                }
//            }
//        }
    }
    

    public void addEmptyProject() {
//        String newProject = "New Project";
//        projectList.add(newProject);
//        customerLabel = curSelProject = newProject;
         
    }

    public void removeSelectedProject() {
        lg.info("onRemoveProjectSelection: " + projectSelection);
        ps.removeProject(projectSelection);
        init();
    }
    
}
