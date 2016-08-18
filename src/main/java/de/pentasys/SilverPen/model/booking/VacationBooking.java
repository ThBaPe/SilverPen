package de.pentasys.SilverPen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Vacation
 *
 */
@Entity
@Table(name = "VACATION")
public class Vacation extends Hour{
 
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(nullable = false)
    private String status;   
    
}