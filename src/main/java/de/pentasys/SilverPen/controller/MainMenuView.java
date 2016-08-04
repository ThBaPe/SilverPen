package de.pentasys.SilverPen.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class MainMenuView {

    public void upload(){
        //Sobald Funktion implementiert wird, Message entfernen
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Es existiert kein Hochladen, es existiert nur Zuul!!", null));
    }
    
    public void invoiced(){
        //Sobald Funktion implementiert wird, Message entfernen
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Noch nicht implementiert!!", null));
    }
    
    public void vacation(){
        //Sobald Funktion implementiert wird, Message entfernen
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Nope", null));
    }
    
    public String changeUser(){
      return "user_mod.xhtml?faces-redirect=true";
    }
    
    public void allUsers(){
      //Sobald Funktion implementiert wird, Message entfernen
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Es existieren keine Benutzer, es existiert nur Zuul!!", null));
    }
}
