package de.pentasys.SilverPen.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.service.LoginInfo;

@Named
@RequestScoped
public class HomeView {

    @Inject
    private LoginInfo loginInfo;

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
    
}
