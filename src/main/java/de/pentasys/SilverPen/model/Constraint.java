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
 * Einzelne Beschränkung
 */

@NamedQueries({ 
    @NamedQuery(name = "Constraint.findByUUID", query = "SELECT c FROM Constraint c Where c.id = :constraint") 
})
@Entity
@Table(name="CONSTRAINT")
public class Constraint extends AbstractUUIDEntity {
    public static final String findByUUID = "Constraint.findByUUID";

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
}