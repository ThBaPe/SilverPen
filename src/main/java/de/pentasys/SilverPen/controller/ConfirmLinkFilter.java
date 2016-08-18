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
 * Filter für die Verarbeitung von Bestätigungslinks
 * @author bankieth
 *
 */
public class ConfirmLinkFilter implements Filter {

    @Inject private LoginInfo curSession;
    @Inject private Logger lg;
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String reqURL = ((HttpServletRequest)request).getRequestURI();
        lg.info("FilterRequest: " + reqURL);
        

        chain.doFilter(request, response);
    }
 
    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }
 
    public void destroy() {
        // Nothing to do here!
    }   
     
}