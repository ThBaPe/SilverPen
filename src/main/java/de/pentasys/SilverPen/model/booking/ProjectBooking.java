package de.pentasys.SilverPen.model.booking;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.pentasys.SilverPen.model.Line;
import de.pentasys.SilverPen.model.Project;

/**
 * Entity implementation class for Entity: ProjectBooking
 *
 */
@Entity
@NamedQueries({ 
    @NamedQuery(name = "ProjectBooking.findSpanByUser", query = "SELECT b FROM ProjectBooking b Where b.user = :user AND TYPE(b) = ProjectBooking  AND ((b.start < :spanStart  AND b.stop > :spanStop) OR ((b.start BETWEEN :spanStart AND :spanStop) OR (b.stop BETWEEN :spanStart AND :spanStop))) ORDER BY b.start"),
    @NamedQuery(name = "ProjectBooking.findSpanByUserOrderStop", query = "SELECT b FROM ProjectBooking b Where b.user = :user AND TYPE(b) = ProjectBooking  AND ((b.start < :spanStart  AND b.stop > :spanStop) OR ((b.start BETWEEN :spanStart AND :spanStop) OR (b.stop BETWEEN :spanStart AND :spanStop))) ORDER BY b.stop")
})
@DiscriminatorValue("Project")
public class ProjectBooking extends BookingItem{

    public static final String findSpanByUser = "ProjectBooking.findSpanByUser";
    public static final String findSpanByUserOrderStop = "ProjectBooking.findSpanByUserOrderStop";
    
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