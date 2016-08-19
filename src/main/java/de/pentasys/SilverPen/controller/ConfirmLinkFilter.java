package de.pentasys.SilverPen.controller;


import java.io.IOException;
import java.util.logging.Logger;


import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.pentasys.SilverPen.model.Constraint.ConstraintType;
import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.service.UserAccountService;
import de.pentasys.SilverPen.util.PageNavigationResult;
 
/**
 * Filter für die Verarbeitung von Bestätigungslinks
 * @author bankieth
 *
 */
public class ConfirmLinkFilter implements Filter {

    @Inject private LoginInfo curSession;
    @Inject private Logger lg;
    @Inject private UserAccountService userService;
    
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String reqURL = ((HttpServletRequest)request).getRequestURI();
        int idx = reqURL.lastIndexOf("/");
        String externalLink = reqURL.substring(idx+1);

        lg.info("FilterRequest: " + reqURL);
        lg.info("UUID: " + externalLink);
        
        
        
        if(curSession.getCurrentUser() != null) {
            String linkID = userService.addConstraint(ConstraintType.LOGIN_CONFIRMATION, curSession.getCurrentUser());
            lg.info("linkID: " + linkID);
            Boolean bDone = userService.callExternalLink(linkID);
            lg.info("bDone: " + bDone);
        }
        
        
        if(!externalLink.isEmpty() 
                && userService.callExternalLink(externalLink)) {
            
            // Link wurde verarbeitet, da wir bisher nur die Registrierungsbestätigung
            // haben, können wir direkt auf die Anmeldung verweisen
            String contextPath = ((HttpServletRequest)request).getContextPath();
            ((HttpServletResponse)response).sendRedirect(contextPath + "/signin.xhtml");
        }

        chain.doFilter(request, response);
    }
 
    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }
 
    public void destroy() {
        // Nothing to do here!
    }   
     
}