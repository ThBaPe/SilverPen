package de.pentasys.SilverPen.controller;


import java.io.IOException;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.util.PageNavigationResult;
 
/**
 * Filter checks if LoginBean has loginIn property set to true.
 * If it is not set then request is being redirected to the login.xhml page.
 * 
 * @author itcuties
 *
 */
public class SigninFilter implements Filter {

    @Inject private LoginInfo curSession;
    @Inject private Logger lg;
    
    /**
     * Checks if user is logged in. If not it redirects to the login.xhtml page.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String reqURL = ((HttpServletRequest)request).getRequestURI();
        lg.info("FilterRequest: " + reqURL);
        
        if(curSession == null || curSession.getCurrentUser() == null) {
            
            lg.info("RequestFaild curSession == null: " + (curSession == null));
            
            String contextPath = ((HttpServletRequest)request).getContextPath();
            ((HttpServletResponse)response).sendRedirect(contextPath + "/signin.xhtml");
            
//            FacesContext.getCurrentInstance().getExternalContext().redirect(PageNavigationResult.USER_SIGNIN.toString());
            
        }

        //lg.info("FilterRequest: User is " + curSession.getCurrentUser().getUsername());
        
        chain.doFilter(request, response);
    }
 
    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }
 
    public void destroy() {
        // Nothing to do here!
    }   
     
}