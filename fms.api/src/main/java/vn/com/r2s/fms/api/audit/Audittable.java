package vn.com.r2s.fms.api.audit;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Audittable<U> {
	
	@CreatedBy
	protected U createBy;
	
	@CreatedBy
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createDate;
	
	@LastModifiedBy
	protected U lastModifiedDate;

	public U getCreateBy() {
		return createBy;
	}

	public void setCreateBy(U createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public U getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(U lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
}
