package de.pentasys.SilverPen.util;

public enum PageNavigationResult {
    
    /**
     * Navigiere zu dem Home-Bereich des
     * angemeldeten Benutzers
     */
    USER_HOME,
    
    /**
     * Navigiere zu der Projektverwaltung
     */
    PROJECT_ADMIN,
    PROJECT_USER_MATCH,

    /**
     * Navigiere zu der Benutzerverwalltung
     */
//  USER_ADMIN, noch nicht vorhanden
    USER_OVERVIEW,
    USER_RIGHTS,
    
    /**
     * Navigiere zu der Benutzeranmeldung
     */
    USER_SIGNIN,
    
    /**
     * Navigiere zu der Benutzerregistrierung
     */
    SIGNUP,             // Freie Benutzerreg.
    SIGNUP_ADMIN;       // Benuzterreg. für angemeldete Admins
    
}
