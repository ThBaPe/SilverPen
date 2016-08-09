package de.pentasys.SilverPen.util;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.service.ProjectService;

@FacesConverter("de.pentasys.ProjectConverter")
public class ProjectConverter implements Converter {

    @Inject
    private ProjectService projService;
    
    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1,
            String arg2) {
        if(arg2 == null || arg2.trim().equals("")){
            return null;
        } else {
            int id = Integer.parseInt(arg2);
            List<Project> projs = projService.getAllProjects();
            for (Project proj : projs){
                if (proj.getId() == id){
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
