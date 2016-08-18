package de.pentasys.SilverPen.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * UUID Entety Basisklasse
 * @author bankieth
 *
 */
@MappedSuperclass
public abstract class AbstractUUIDEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AbstractUUIDEntity() {
            this.id = UUID.randomUUID().toString();
        }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof AbstractUUIDEntity)) {
            return false;
        }
        AbstractUUIDEntity other = (AbstractUUIDEntity) obj;
        return getId().equals(other.getId());
    }

}
