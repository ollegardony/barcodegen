package hu.barcode.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(name = "modify_dt", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date modifyDt;

	@Column(name = "create_dt", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createyDt;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}

	public Date getCreateyDt() {
		return createyDt;
	}

	public void setCreateyDt(Date createyDt) {
		this.createyDt = createyDt;
	}

	@Override
	public int hashCode() {
		if (null == id) {
			return 0;
		} else {
			return id.hashCode();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (null == id) {
			return super.equals(obj);
		}
		if (null == obj) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!getEqualsClass().isInstance(obj)) {
			return false;
		}
		BaseEntity e = (BaseEntity) obj;
		return id.equals(e.getId());
	}

	protected abstract Class<? extends BaseEntity> getEqualsClass();
	
	@PrePersist
    protected void onCreate() {
		modifyDt = createyDt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
    	modifyDt = new Date();
    }
}

