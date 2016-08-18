package de.pentasys.SilverPen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * Beschränkungen die für Benutzer gesetzt werden können
 * @author bankieth
 *
 */
@NamedQueries({ 
    @NamedQuery(name = "Constraint.findByUUID",         query = "SELECT c FROM Constraint c Where c.id = :constraint"), 
    @NamedQuery(name = "Constraint.findByUser",         query = "SELECT c FROM Constraint c Where c.user = :user"),
    @NamedQuery(name = "Constraint.findByUserAndType",  query = "SELECT c FROM Constraint c Where c.user = :user AND c.type = :type")
})
@Entity
@Table(name="CONSTRAINT")
public class Constraint extends AbstractUUIDEntity {
    public static final String findByUUID = "Constraint.findByUUID";
    public static final String findByUser = "Constraint.findByUser";
    public static final String findByUserAndType = "Constraint.findByUserAndType";
    

    public enum ConstraintType{
        LOGIN_CONFIRMATION
    }

    @Column(nullable=false)
    private java.util.Date pinDate;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private ConstraintType type;
    
    @ManyToOne
    private User user;

    public java.util.Date getPinDate() {
        return pinDate;
    }

    public void setPinDate(java.util.Date pinDate) {
        this.pinDate = pinDate;
    }

    public ConstraintType getType() {
        return type;
    }

    public void setType(ConstraintType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}