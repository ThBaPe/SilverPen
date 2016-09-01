package de.pentasys.SilverPen.model.booking;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.pentasys.SilverPen.model.Line;
import de.pentasys.SilverPen.model.Project;

/**
 * Entity implementation class for Entity: ProjectBooking
 *
 */
@Entity
@Table(name = "PROJECTBOOKING")
@DiscriminatorValue("Project")
public class ProjectBooking extends BookingItem{

    public enum StatusProjectTime{
        TIME_BILLED,
        TIME_NOTBILLED
    }
    
    @ManyToOne
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    
}