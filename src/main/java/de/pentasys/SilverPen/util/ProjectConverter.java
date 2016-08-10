package de.pentasys.SilverPen.util;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.service.ProjectService;


@Named
public class ProjectConverter implements Converter {

    @Inject
    private ProjectService projService;
    
    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1,
            String arg2) {
        if(arg2 == null || arg2.trim().equals("")){
            return null;
        } else {
            List<Project> projs = projService.getAllProjects();
            String[] projectArray = arg2.split("-");
            String id = projectArray[0];
            id = id.substring(0, (id.length()-1));
            for (Project proj : projs){
                if (proj.getProjectnumber().equals(id)){
                    return proj;
                }
            }
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1,
            Object arg2) {
        if (arg2 == null || arg2.equals("")) {  
            return "";  
        } else {
            return ((Project)arg2).toString();
        }
    }

}
