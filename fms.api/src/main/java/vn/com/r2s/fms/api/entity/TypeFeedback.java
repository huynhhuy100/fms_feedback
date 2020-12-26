package vn.com.r2s.fms.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import vn.com.r2s.fms.api.audit.Audittable;

@Entity
@Table(name = "typefeedback")
@EntityListeners(AuditingEntityListener.class)
public class TypeFeedback extends Audittable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int typeID;
	
	@Column(name = "type_name",nullable = false)
	private String typeName;
	
	@Column(name = "isDeleted",nullable = true)
	private boolean isDeleted;
	

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
}
