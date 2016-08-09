package de.pentasys.SilverPen.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.service.ProjectService;



@Named
@RequestScoped
public class ProjectView {

    private String customerLabel;
    private String curSelection;
    private java.util.Date projectStart;
    private List<Project> projectList;


    public String getCurSelection() {
        return curSelection;
    }

    public void setCurSelection(String curSelection) {
        this.curSelection = curSelection;
    }

    @Inject private Logger lg;
    @Inject private ProjectService ps;
    
    @PostConstruct
    public void init(){
        if(projectList == null) {
            projectList = ps.getAllProjects();
        }
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

        projectList.add(pro);
    }

    public void onSelect(SelectEvent event) {

        lg.info("onSelect: " + event.getObject());
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
        
//        lg.info("Projekt Remove: Start");
//        
//        if(!curSelProject.isEmpty()){
//            int iListSize = projectList.size();
//            for (int i = 0; i < iListSize; i++) {
//                if(projectList.get(i).equals(curSelProject))
//                {
//                    lg.info("Projekt Remove: " + curSelProject);
//                    projectList.remove(i);
//                    curSelProject = projectList.isEmpty() ? "" : projectList.get(0) ;
//                    break;
//                }
//            }
//        }
        
    }
    
    
}
